package com.nanomt88.demo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  异步处理request 任务类
 * @author nanomt88@gmail.com
 * @create 2021-08-28 6:45
 **/
@Slf4j
public class AsyncRequestProcessor implements Runnable {

    private AsyncContext asyncContext ;

    public AsyncRequestProcessor(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {
        log.info("async task start...");
        try {
            BufferedReader reader = asyncContext.getRequest().getReader();
            String requestBody = readRequestBody(reader);
            log.info("request body: " + requestBody);

            Thread.sleep(200);

//            PrintWriter writer = asyncContext.getResponse().getWriter();
//            writer.write(requestBody);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            //异步完成
            asyncContext.complete();
        }
    }

    private String readRequestBody(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String str = null;
        while ( (str = reader.readLine()) != null){
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
