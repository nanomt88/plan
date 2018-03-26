package com.nanomt88.study.designpattern.proxy.custom;

import java.lang.reflect.Method;

/**
 * 自己写一个 InvocationHandle的接口 ，替代JDK的InvocationHandle 实现动态代理
 */
public interface MyInvocationHandler {

    /**
     *  自定义 InvocationHandle的 invoke方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
