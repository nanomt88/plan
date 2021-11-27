package com.nanomt88.demo.gateway.norch;

import com.nanomt88.demo.gateway.channel.AbstractTcChannel;
import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcNorthGatewayChannel extends AbstractTcChannel {


    @Override
    public void doInnerProcess(TcRequest request, TcResponse response) {
        doServerRequestPreProcess(request, response);
        doRequestProcess(request,response);
        doClientRequestPreProcess(request ,response);
        //远程调用
        super.client.doInvoke(request, response);
        doClientRequestAfterProcess(request, response);
        doResponseProcess(request,response);
        doResponsePerProcessor(request, response);
    }

}
