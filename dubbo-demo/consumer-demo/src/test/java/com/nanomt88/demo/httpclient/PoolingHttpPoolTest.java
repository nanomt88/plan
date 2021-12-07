package com.nanomt88.demo.httpclient;

import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.dubbo.PoolingHttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

//@SpringBootTest
@Slf4j
public class PoolingHttpPoolTest {


    @Test
    public void doGetTest() throws InterruptedException {
        PoolingHttpClientUtils.init();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int j = 0; j< 10 ; j++) {
            final int jj = j;
            new Thread(() -> {
                for(int i = 0; i< 10 ; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", "张三" + i);
                    jsonObject.put("age", jj+"-" + i);
                    String response = PoolingHttpClientUtils.doJsonPost("http://127.0.0.1:8082/api/test", jsonObject);
                    System.out.println(response);
                }
                countDownLatch.countDown();
            }).start();

        }
        countDownLatch.await();
    }

    /**
     * 测试 http请求超时设置 ，结果：可以根据每个请求设置超时时间
     *   //测试结果，超时时间3秒的请求没问题，超时时间1秒的请求报错
     *
     * 测试的时候 com.nanomt88.demo.dubbo.generic.MyGenericController.test() 需要设置睡眠时间1.5秒
     *      doJsonPost 设置超时时间3秒
     *      doJsonPost2  设置超时时间位1秒
     * @throws InterruptedException
     */
    @Test
    public void doTimeoutTest() throws InterruptedException {
        PoolingHttpClientUtils.init();

        for(int j = 0; j< 6 ; j++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "张三" + j);
            jsonObject.put("age", "2"+j);
            if(j % 2 == 0){
                PoolingHttpClientUtils.doJsonPost("http://127.0.0.1:8082/api/test", jsonObject);
            }else {
                PoolingHttpClientUtils.doJsonPost2("http://127.0.0.1:8082/api/test", jsonObject);
            }
        }
    }

}
