package com.nanomt88.study.designpattern.proxy.custom;

import com.nanomt88.study.designpattern.proxy.House;
import com.nanomt88.study.designpattern.proxy.Renter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyBadAgent implements MyInvocationHandler {

    private Renter target;

    public Object getInstance(Renter renter){
        this.target = renter;
        Class<?> clazz = renter.getClass();
        return MyProxy.newProxyInstance(new MyClassLoader(MyClassLoader.class.getResource("/").getPath()),
                clazz.getInterfaces(), this);
    }

    public Renter getTarget() {
        return target;
    }

    public void setTarget(Renter target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------ 我是黑中介2 ------");
        System.out.println("proxy : "+proxy.getClass());
        System.out.println("推荐备选房子2 ： " + args[0].toString());
        House h = (House) args[0];
        boolean result = true;
                method.invoke(target,h);
        if(result){
            System.out.println("推荐房子成功2……");
            System.out.println("------ 我是黑中介2 ------");
            return result;
        }
        System.out.println("------ 我是黑中介2 ------");
        return false;
    }
}
