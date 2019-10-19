package io.github.biezhi.wechat.utils;

import java.util.Random;

/**
 * 线程睡眠
 *
 * @author nanomt88@gmail.com
 * @create 2019-01-05 19:29
 **/
public class ThreadUtil {

    /**
     * 随机睡眠 1 - 3秒
     */
    public static final void sleepRandom(){
        Random random  = new Random();
        long nextInt = random.nextInt(2000);
        try {
            Thread.currentThread().sleep((nextInt + 1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机睡眠 3-4秒
     */
    public static final void sleepRandom2(){
        Random random  = new Random();
        long nextInt = random.nextInt(1000);
        try {
            Thread.currentThread().sleep((nextInt + 3000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final void sleep(long millis){
        try {
            Thread.currentThread().sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
