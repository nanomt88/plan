package com.nanomt88.study.designpattern.proxy.cglib;

import com.nanomt88.study.designpattern.proxy.House;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BadAgentV2 implements MethodInterceptor {

    public Object getInstance(Class<?> clazz, String name, House house){
        Enhancer enhancer = new Enhancer();
        //设置回调，当前类
        enhancer.setCallback(this);
        //告诉cglib 生成的子类需要继承哪个父类
        enhancer.setSuperclass(clazz);
        //create无参构造方法调用 clazz对象的默认无参方法； 如果需要调用有参构造，需要传入参数
        return enhancer.create(new Class<?>[]{String.class, House.class}, new Object[]{name, house});
    }

    @Override
    public Object intercept(Object proxyObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        System.out.println("proxy : "+ proxyObj.getClass());
        System.out.println("推荐备选房子 ： " + args[0].toString());
        boolean result = (boolean) methodProxy.invokeSuper(proxyObj, args);;
        if(result){
            System.out.println("推荐房子成功……");
            System.out.println("------ 我是黑中介 ------");
            return result;
        }
        System.out.println("------ 我是黑中介 ------");
        return false;
    }
}
