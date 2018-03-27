package com.nanomt88.study.designpattern.proxy.custom;


import com.nanomt88.study.designpattern.proxy.BeijingLoser;
import com.nanomt88.study.designpattern.proxy.House;
import com.nanomt88.study.designpattern.proxy.Renter;

/**
 * ${DESCRIPTION}
 *
 * @author hongxudong
 * @create 2018-03-10 21:00
 **/
public class MyClient {
    public static void main(String[] args) {
        test1();
//        test2();
//        test3();
    }

    /**
     * 动态代理原理 ：
     *  1.拿到代理的对象（new BeijingLoser），然后获取其接口类型：Renter
     *  2.JDK 代理重新生成一个类 （Proxy.newProxyInstance），同时实现我们给代理对象所实现的接口（Renter）
     *  3.把代理对象的引用也拿到了 （ds.target）
     *  4.重新动态生成一个class字节码 （ds）
     *  5.最后编译，调用被代理类的方法（ds.findHouse）
     */
    public static void test1() {
        MyBadAgent agent = new MyBadAgent();
        Renter ds = (Renter) agent.getInstance(new BeijingLoser("屌丝王",
                new House(true, true, 800, 8)));
        System.out.println("代理类::::::::"  + ds.getClass());
        House house = new House(false, true, 2000, 20);
//        System.out.println("推荐备选房子1： " + house.toString());
        boolean result = ds.findHouse(house);
        if(result){
            return;
        }
        house = new House(true, true, 800, 10);
//        System.out.println("推荐备选房子2： " + house.toString());
        result = ds.findHouse(house);
        if(result){
            return;
        }
    }

    public static void test2(){
        MyClassLoader classLoader = new MyClassLoader(MyClassLoader.class.getResource("/").getPath());
        try {
            Class<?> aClass = classLoader.loadClass(MyDemo.class.getName());
            MyDemo o = (MyDemo) aClass.newInstance();
            o.invoke();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void test3(){
        MyInvocationHandler h = new MyBadAgent();
        $Proxy0 proxy = new $Proxy0(h);
        proxy.findHouse(new House(true, true, 100, 100));
    }
}