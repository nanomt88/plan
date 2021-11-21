package com.nanomt88.demo.gateway.channel;

import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;

/**
 *  服务端接受到请求后，处理报文的类，参考 netty的 channel定义
 */
public interface ITcChannel {

    /**
     * 组装 服务端 TcChannel通道的内部处理过程
     * @param request
     * @param response
     */
    void doInnerProcess(TcRequest request, TcResponse response);

}
