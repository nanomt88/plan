package com.nanomt88.demo.gateway.channel.router;

import com.nanomt88.demo.gateway.channel.ITcChannel;
import com.nanomt88.demo.gateway.common.TcHttpRequest;
import com.nanomt88.demo.gateway.common.TcRequest;
import lombok.Data;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * 根据url进行分发的规则
 */
@Data
public class HttpLocationDispatcherRule implements IDispatcherRule{

    private ITcChannel channel;

    private List<String> locations;

    PathMatcher pathMatcher  = new AntPathMatcher();

    @Override
    public boolean isMatch(TcRequest request) {
        if(!(request instanceof TcHttpRequest) || CollectionUtils.isEmpty(locations)){
            return false;
        }
        TcHttpRequest httpRequest = (TcHttpRequest) request;

        for(String patten : locations){
            if(pathMatcher.match(patten , httpRequest.getUrl() )){
                return true;
            }
        }
        return false;
    }

    @Override
    public ITcChannel getTcChannel() {
        return channel;
    }

    public void setChannel(ITcChannel channel) {
        this.channel = channel;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
