package com.nanomt88.study.designpattern.strategy.version;


/**
 * spring 源码中 关于URL version提取的策略模式 示例
 *
 * @author nanomt88@gmail.com
 * @date 18.5.16 7:22
 * @updateLog:
 *      update by nanomt88@gmail.com, 18.5.16 7:22
 */
public interface VersionStrategy {

    /**
     *  通过request url 提取version 版本
     * @param requestPath
     * @return
     */
    String extractVersion(String requestPath);

    /**
     * 根据请求路径 删除指定version版本
     *
     * @param requestPath the request path of the resource being resolved
     * @param version     the version obtained from {@link #extractVersion(String)}
     * @return the request path with the version removed
     */
    String removeVersion(String requestPath, String version);

    /**
     *  给给定的请求路径添加一个版本
     *
     * @param requestPath the requestPath
     * @param version     the version
     * @return the requestPath updated with a version string
     */
    String addVersion(String requestPath, String version);

}
