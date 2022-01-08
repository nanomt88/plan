package com.nanomt88.demo.proxy;

/**
 * 模拟数据库入库
 *
 * @author nanomt88@gmail.com
 * @create 2022/1/8 11:48
 **/
public class TargetProxyImpl implements ITargetProxy {
    @Override
    public void save(String id) {
        System.out.printf("保存数据入库了：%s \n" ,id);
    }
}
