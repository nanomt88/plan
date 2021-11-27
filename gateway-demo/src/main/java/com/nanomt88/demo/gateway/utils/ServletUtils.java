package com.nanomt88.demo.gateway.utils;

import javax.servlet.AsyncContext;
import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author nanomt88@gmail.com
 * @create 2021-08-28 23:33
 **/
public class ServletUtils {

    /**
     * 读取请求参数
     * @param asyncContext
     * @return
     * @throws IOException
     */
    public static String readRequestBody(AsyncContext asyncContext) throws IOException {
        BufferedReader reader = asyncContext.getRequest().getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String str = null;
        while ( (str = reader.readLine()) != null){
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    /**
     * 读取请求参数
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readRequestBody(ServletInputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String str = null;
        while ( (str = bufferedReader.readLine()) != null){
            stringBuilder.append(str);

        }
        return stringBuilder.toString();
    }

}
