package com.nanomt88.demo.gateway.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;

/**
 * @author nanomt88@gmail.com
 * @create 2021-08-28 6:38
 **/
@Slf4j
public class AppAsyncListener implements AsyncListener {
    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        log.info("request listener complete...");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        log.info("request listener timeout...");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        log.info("request listener error...");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        log.info("request listener error...");
    }
}
