package com.nanomt88.demo.ioc;

/**
 * \
 *
 * @author nanomt88@gmail.com
 * @create 2021-10-03 9:29
 **/
public class TestIoc {
    public static void main(String[] args) {
        IocA a = new IocA();
    }
}

class IocA {
    IocB b ;
    public IocA(){
        b = new IocB();
    }
}

class IocB {
    IocA a ;
    public IocB(){
        a = new IocA();
    }
}