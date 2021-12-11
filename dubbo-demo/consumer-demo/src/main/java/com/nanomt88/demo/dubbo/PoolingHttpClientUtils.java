package com.nanomt88.demo.dubbo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 对于过期链接的处理，当Tomcat主动关闭链接时，httpclient 4.4之前是每次在复用链接前进行检查链接是否可用，
 * http4.4后，是自上次使用连接以来所经过的时间超过已设置的超时时（默认超时设置为2000ms），才检查连接。
 * 如果发现链接不可用，则从链接池剔除，在创建新的链接。
 * <p>
 * 当客户端设置的TTL到期时（此时Tomcat容器没有主动关闭链接时），在每次发起请求时，会检查链接是否已经过期，
 * 如果过期（虽然链接本身是可以用的），则也主动关闭链接，然后从链接池剔除，在创建新的链接。
 * <p>
 * 另外我们可以实现自己的ConnectionKeepAliveStrategy来给不同的域名设置不同的链接存活策略。
 * @author nanomt88
 */
@Slf4j
public class PoolingHttpClientUtils {

    private static PoolingHttpClientConnectionManager connectionManager = null;

    private static ConnectionConfig connectionConfig = null;

    public static void init() {

        //对于Tomcat服务器默认保持客户端的链接60s,我们httpclient这边也可以设置链接存活时间，最终链接的存活时间是取两者中最小的。
        //60秒连接存活时间
        connectionManager = new PoolingHttpClientConnectionManager(60, TimeUnit.SECONDS);
        //全局最大的连接数
        connectionManager.setMaxTotal(10);
        //每个路由维护和最大连接数限 ；注意这里路由是指IP+PORT或者指域名
        connectionManager.setDefaultMaxPerRoute(2);

        connectionConfig = ConnectionConfig.custom().setCharset(Charset.forName("UTF-8")).build();

    }

    private static ConnectionKeepAliveStrategy customConnectionKeepAliveStrategy(){

        ConnectionKeepAliveStrategy kaStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    //如果服务器没有设置keep-alive这个参数，我们就把它设置成1分钟
                    keepAlive = 60000;
                }
                return keepAlive;
            }

        };
        return kaStrategy;
    }

    public static CloseableHttpClient buildHttpClient() {
        //创建http连接
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultConnectionConfig(connectionConfig)
                .setConnectionManager(connectionManager)
//                .setKeepAliveStrategy(customConnectionKeepAliveStrategy()) //设置自定义连接保活策略
                .disableAutomaticRetries().build();
        return httpClient;
    }

    public static String doJsonPost2(String url, JSONObject data) {
        CloseableHttpResponse httpResponse = null;
        try {
            //创建 http post 对象
            HttpPost httpPost = new HttpPost(url);
            RequestConfig.Builder requestBuilder = RequestConfig.custom();
            requestBuilder.setSocketTimeout(1000) //设置客户端等待服务端返回数据的超时时间
                    .setConnectTimeout(1000)  //设置客户端发起TCP连接请求的超时时间
                    .setConnectionRequestTimeout(1000);  //设置客户端从连接池获取链接的超时时间
            httpPost.setConfig(requestBuilder.build());
            httpPost.setEntity(new StringEntity(data.toString(), ContentType.APPLICATION_JSON));
            httpResponse = buildHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.warn("http调用出错，状态码：[{}], body:[{}]", httpResponse.getStatusLine().getStatusCode(),
                        EntityUtils.toString(httpResponse.getEntity()));
                return null;
            }
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            log.info("响应 ： [{}]", responseString);

            return responseString;
        } catch (IOException e) {
            log.error("httpclient get error", e);
        } finally {
            //回收链接到连接池
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
//                    httpPost.releaseConnection(); 释放基础HTTP连接以允许重用它 ；
//                    4.2中，甚至可能在该版本之前，要遵循的正确模式是不要使用releaseConnection
//                    httpResponse.close();
                    //归还链接不要调用response对象的close（）方法，因为其是关闭链接，而不是把链接返回到链接池
                } catch (IOException e) {
                    log.error("httpclient release to pooling error", e);
                }
            }
        }
        return null;
    }

    public static String doJsonPost(String url, JSONObject data) {
        CloseableHttpResponse httpResponse = null;
        try {
            //创建 httpget 对象
            HttpPost httpPost = new HttpPost(url);
            RequestConfig.Builder requestBuilder = RequestConfig.custom();
            requestBuilder.setSocketTimeout(3000) //设置客户端等待服务端返回数据的超时时间
                    .setConnectTimeout(1000)  //设置客户端发起TCP连接请求的超时时间
                    .setConnectionRequestTimeout(3000);  //设置客户端从连接池获取链接的超时时间
            httpPost.setConfig(requestBuilder.build());
            httpPost.setEntity(new StringEntity(data.toString(), ContentType.APPLICATION_JSON));
            httpResponse = buildHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.warn("http调用出错，状态码：[{}], body:[{}]", httpResponse.getStatusLine().getStatusCode(),
                        EntityUtils.toString(httpResponse.getEntity()));
                return null;
            }
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            log.info("响应 ： [{}]", responseString);
            return responseString;
        } catch (IOException e) {
            log.error("httpclient get error", e);
        } finally {
            //回收链接到连接池
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    //归还链接不要调用response对象的close（）方法，因为其是关闭链接，而不是把链接返回到链接池
                } catch (IOException e) {
                    log.error("httpclient release to pooling error", e);
                }
            }
        }
        return null;
    }
}
