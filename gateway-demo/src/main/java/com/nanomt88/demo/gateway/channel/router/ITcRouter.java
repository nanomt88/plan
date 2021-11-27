package com.nanomt88.demo.gateway.channel.router;

import com.nanomt88.demo.gateway.channel.ITcChannel;
import com.nanomt88.demo.gateway.common.TcRequest;

/**
 *   路由接口类，用于根据请求选择当前的 channel处理实例
 */
public interface ITcRouter {

    /**
     * 选择当前请求支持处理的TcChannel类
     * @param request
     * @return
     */
    ITcChannel selectChannel(TcRequest request);
}
