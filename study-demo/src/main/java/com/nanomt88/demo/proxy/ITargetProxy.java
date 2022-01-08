package com.nanomt88.demo.proxy;

/**
 * 目标代理类
 */
public interface ITargetProxy {

    /**
     * 模拟保存数据入库
     * @param id
     */
    void save(String id);
}
