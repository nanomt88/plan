package com.nanomt88.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO
 *
 * @author nanomt88@gmail.com
 * @create 2022/1/8 11:45
 **/
public class ProxyTest {

    public static void main(String[] args) {
        jdkProxy();
        cglibProxy();
    }

    public static void jdkProxy() {
        ITargetProxy target = new TargetProxyImpl();
        ProxyAdvice advice = new ProxyAdvice();
        ITargetProxy proxyInstance = (ITargetProxy) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), (proxy, method, args) -> {
            String id = "";
            if (args != null && args.length > 0) {
                id = String.valueOf(args[0]);
            }else {
                System.out.println("未获取到请求参数");
            }
            advice.before(id);
            Object invoke = method.invoke(target, args);
            advice.after(id);
            return invoke;
        });
        proxyInstance.save("1000");
    }

    public static void cglibProxy() {
        //目标对象
        ITargetProxy target = new TargetProxyImpl();
        //增强对象
        ProxyAdvice advice = new ProxyAdvice();

        //1. 创建增强器
        Enhancer enhancer = new Enhancer();
        //2. 设置父目标
        enhancer.setSuperclass(target.getClass());
        //3. 设置回调
        enhancer.setCallback((MethodInterceptor) (proxyObject, proxyMethod, args, methodProxy) -> {
            String id = "";
            if (args != null && args.length > 0) {
                id = String.valueOf(args[0]);
            }else {
                System.out.println("未获取到请求参数");
            }
            advice.before(id);
            Object invoke = proxyMethod.invoke(target, args);
            advice.after(id);
            return invoke;
        });
        //4. 创建代理对象
        ITargetProxy targetProxy = (ITargetProxy) enhancer.create();
        targetProxy.save("2000");
    }
}
