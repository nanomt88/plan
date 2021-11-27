package com.nanomt88.demo.lock.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 实现cas操作，并解决ABA的问题
 *
 * @author nanomt88@gmail.com
 * @create 2020-11-29 15:48
 **/
public class CasThread2 {

    /**
     * 转账
     * @param balance
     * @param addMount
     * @return
     */
    private static boolean transfer(AtomicStampedReference<Integer> balance, int addMount){
        //使用cas指令判断是否为设置成功，未成功则循环继续设置
        while (!balance.compareAndSet(balance.getReference(),
                new Integer(balance.getReference() + addMount), balance.getStamp(),
                balance.getStamp() + 1)){
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {

        Integer integer = new Integer(0);
        final AtomicStampedReference<Integer> balance = new AtomicStampedReference(integer,0);
        //模拟10个线程更新余额
        CountDownLatch latch = new CountDownLatch(10);
        for(int i=0; i < 10 ; i++){
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for(int j=0; j < 1000 ; j++) {
                        if(finalI % 2 == 0) {
                            transfer(balance, 1);
                        }else {
                            transfer(balance, -1);
                        }
                    }
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        System.out.println("账户余额：" + balance.getReference());
    }




}
