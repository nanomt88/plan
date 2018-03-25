package com.nanomt88.study.designpattern.proxy.myproxydemo;

import com.nanomt88.study.designpattern.proxy.Renter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyBadAgent implements InvocationHandler {

    private Renter renter;

    public Object getInstance(){
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
