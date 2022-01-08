package com.nanomt88.demo.proxy;

/**
 * 代理增强类，用于增加ITargetProxy类
 *
 * @author nanomt88@gmail.com
 * @create 2022/1/8 11:52
 **/
public class ProxyAdvice {

    public void  before(String id){
        System.out.printf("在方法增强之前调用：%s \n", id);
    }

    public void after(String id){
        System.out.printf("在方法增强之后调用：%s \n", id);
    }
}
