package com.nanomt88.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author nanomt88@gmail.com
 * @create 2020-11-21 16:34
 **/
public class CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        runAsyncExample();
    }


    static void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertFalse(cf.isDone());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(cf.isDone());

        cf.thenCompose(null);
    }


    public static void assertTrue(boolean flag) {
        assert flag;
    }

    public static void assertFalse(boolean flag) {
        assert !flag;
    }
}
