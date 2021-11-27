package com.nanomt88.demo.gateway.channel.router;

import com.nanomt88.demo.gateway.channel.ITcChannel;
import com.nanomt88.demo.gateway.common.TcRequest;

public interface IDispatcherRule {
    /**
     * 根据表达式规则，匹配当前请求是否能够处理，能够处理则可以返回对于的channel对象进行处理
     * @param request
     * @return
     */
    boolean isMatch(TcRequest request);
    
    ITcChannel getTcChannel();
}
