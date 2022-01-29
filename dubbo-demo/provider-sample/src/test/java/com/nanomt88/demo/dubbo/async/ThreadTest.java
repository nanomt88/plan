package com.nanomt88.demo.dubbo.async;

import java.nio.charset.Charset;
import java.util.concurrent.locks.LockSupport;

public class ThreadTest {

    public static void main(String[] args) {

        System.out.println(Charset.defaultCharset().toString());

        System.out.println("init ");

        Thread curr = Thread.currentThread();
        new Thread(() -> {
            int i=0;
            while (i< 2) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            System.out.println("中断线程");
            curr.stop();

        }).start();

        while (true){
            try {
                Thread.sleep(100);
                System.out.println("runing....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
