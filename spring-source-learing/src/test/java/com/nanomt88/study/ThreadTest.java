package com.nanomt88.study;

import org.junit.Test;
import sun.misc.Unsafe;

public class ThreadTest {

    @Test
    public void sleepTest(){
        Runnable r  = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable init...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable end...");
            }
        };

        Thread s = new Thread(r);
        System.out.println("start : "+s.getState());
        s.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end : "+s.getState());
        Unsafe ss  =  Unsafe.getUnsafe();
    }
}
