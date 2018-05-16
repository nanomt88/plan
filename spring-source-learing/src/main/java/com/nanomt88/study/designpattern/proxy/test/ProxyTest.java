package com.nanomt88.study.designpattern.proxy.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *   jdk动态代理 和 cglib动态代理 的对比
 *      在jdk1.8 下进行测试，方向jdk的性能优于cglib 。。。。
 * @author nanomt88@gmail.com
 * @date 18.5.13 20:02
 * @updateLog: 
 *      TODO  update by nanomt88@gmail.com, 18.5.13 20:02
 */
public class ProxyTest {

    @Test
    public void prefTest(){

        ProxyInft p1 = DynamicProxyTest.newProxyInstance(new ProxyImpl());
        ProxyInft p2 = CglibProxyTest.newProxyInstance(ProxyImpl.class);

        run(p1, 100);
        run(p2, 100);

        runWithTime(p1, 1000000, "JDK");
        runWithTime(p2, 1000000, "cglib");
    }

    private void  run(ProxyInft p, int count){
        for (int i = 0 ; i< count; i++){
            p.test(i);
        }
    }

    private void  runWithTime(ProxyInft p, int count, String tag){
        long start = System.currentTimeMillis();
        for (int i = 0 ; i< count; i++){
            p.test(i);
        }
        long end = System.currentTimeMillis();
        System.out.println( tag + " run time : " + (end-start) + "ms");
    }
}

interface ProxyInft {
    public int test(int i);
}

class ProxyImpl implements ProxyInft{
    public int test(int i) {
        return i+1;
    }
}

/**
 * jdk动态代理
 */
 class DynamicProxyTest implements InvocationHandler {
    private ProxyInft target;

    private DynamicProxyTest(ProxyInft target) {
        this.target = target;
    }

    public static ProxyInft newProxyInstance(ProxyInft target) {
        return (ProxyInft) Proxy
                .newProxyInstance(DynamicProxyTest.class.getClassLoader(),
                        new Class<?>[] { ProxyInft.class },
                        new DynamicProxyTest(target));

    }
     @Override
     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         return method.invoke(target, args);
     }
 }

/**
 * cglib 动态代理
 */
class CglibProxyTest implements MethodInterceptor {

    private CglibProxyTest() {
    }

    public static <T extends ProxyInft> ProxyInft newProxyInstance(Class<T> targetInstanceClazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetInstanceClazz);
        enhancer.setCallback(new CglibProxyTest());
        return (ProxyInft) enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        return proxy.invokeSuper(obj, args);
    }
}
