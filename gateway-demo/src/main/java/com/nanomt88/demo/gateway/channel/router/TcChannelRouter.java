package com.nanomt88.demo.gateway.channel.router;

import com.nanomt88.demo.gateway.channel.ITcChannel;
import com.nanomt88.demo.gateway.common.TcRequest;
import lombok.Setter;

import java.util.List;

/**
 *  根据请求，识别选择对应的channel处理类
 */
@Setter
public class TcChannelRouter implements ITcRouter {

    List<IDispatcherRule> rules;

    @Override
    public ITcChannel selectChannel(TcRequest request) {
        for (IDispatcherRule rule : rules){
            if(rule.isMatch(request)){
                return rule.getTcChannel();
            }
        }
        return null;
    }
}
