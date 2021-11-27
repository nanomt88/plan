package com.nanomt88.demo.concurrent;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  简单实现一个自增的原子类
 *
 * @author nanomt88@gmail.com
 * @create 2021-08-21 16:11
 **/
public class MyAtomicInteger {

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    volatile Integer value ;

    public MyAtomicInteger(int value){
        value = new Integer(value);
    }

    public int  getValue(){
        return value;
    }

    public void incurrent(){
        for(;;){
            int origin = value;
            int value =origin+1;
            //TODO 如何自己写一个 原子自增类
//            unsafe.compareAndSwapInt(value, )
        }
    }

    public static void main(String[] args) {



    }
}
