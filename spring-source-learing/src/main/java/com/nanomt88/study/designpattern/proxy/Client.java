package com.nanomt88.study.designpattern.proxy;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author hongxudong
 * @create 2018-03-10 21:00
 **/
public class Client {
    public static void main(String[] args) {
//        test1();
        test2();
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
        BadAgent agent = new BadAgent();
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

    /**
     * 生成 运行阶段 的字节码
     */
    private static void test2(){
        BadAgent agent = new BadAgent();
        Renter ds = (Renter) agent.getInstance(new BeijingLoser("屌丝王",
                new House(true, true, 800, 8)));
        try {
//            ProxyGenerator.generateProxyClass()
            System.getProperties().put(
                    "sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            // 第3个参数，是指定编译源码的版本
            byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{ds.getClass()});
            System.out.printf(System.getProperty("java.version"));
            String path = agent.getClass().getResource("/").getPath();
            path += agent.getClass().getPackage().getName().replace(".",File.separator);
            FileOutputStream stream = new FileOutputStream(path + File.separator + "$Proxy0.class");
            stream.write(bytes);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
