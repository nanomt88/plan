package com.nanomt88.demo.lock.cas;

import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nanomt88@gmail.com
 * @create 2020-11-29 11:01
 **/
public class CasThread {

    /**
     * 转账
     * @param balance
     * @param addMount
     * @return
     */
    private static boolean transfer(AtomicInteger balance, int addMount){
        //使用cas指令判断是否为设置成功，未成功则循环继续设置
        while (!balance.compareAndSet(balance.get(), balance.get()+addMount)){
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {

        final AtomicInteger balance = new AtomicInteger(0);
        //模拟10个线程更新余额
        CountDownLatch latch = new CountDownLatch(10);
        for(int j=0; j < 10 ; j++){
            int finalJ = j;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for(int i=0; i < 10000 ; i++) {
                        transfer(balance, 1);
                    }
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        System.out.println("账户余额：" + balance.get());
    }




}
