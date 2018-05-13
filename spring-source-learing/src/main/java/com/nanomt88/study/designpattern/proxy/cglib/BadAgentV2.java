package com.nanomt88.study.designpattern.proxy.cglib;

import com.nanomt88.study.designpattern.proxy.House;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib代理类 示例
 */
// 定义一个实现了MethodInterceptor接口的回调类，类似JDK动态代理的InvocationHandler
public class BadAgentV2 implements MethodInterceptor {

    public Object getInstance(Class<?> clazz, String name, House house){
        Enhancer enhancer = new Enhancer();
        //设置回调，当前类
        enhancer.setCallback(this);
        //告诉cglib 生成的子类需要继承哪个父类
        enhancer.setSuperclass(clazz);
        /*
        步骤同 Java 动态代理模式：
        1. 生成源代码
        2. 编译成class文件
        3. 加载到 JVM中
        4. 生成被代理对象
         */
        //create无参构造方法调用 clazz对象的默认无参方法； 如果需要调用有参构造，需要传入参数
        return enhancer.create(new Class<?>[]{String.class, House.class}, new Object[]{name, house});
    }

    // 实现回调处理方法
    @Override
    public Object intercept(Object proxyObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        System.out.println("proxy : "+ proxyObj.getClass());
        System.out.println("推荐备选房子 ： " + args[0].toString());
         /*
         * proxy是代理方法，所以这里必须要通过proxy.invokeSuper(obj, args)来调用原来Student类
         * 中的方法，如果这里是proxy.invoke(obj, args),则调用的还是proxy方法本身，从而导致无限
         * 递归，注意一定不要调用错误了
         */
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
