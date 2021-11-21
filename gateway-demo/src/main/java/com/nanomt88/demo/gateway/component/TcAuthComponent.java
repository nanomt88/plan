package com.nanomt88.demo.gateway.component;

import com.nanomt88.demo.gateway.GatewayExceptioin.GatewayException;
import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import org.springframework.util.StringUtils;

import javax.security.auth.message.AuthException;

public class TcAuthComponent implements ITcComponent{

    private static final String TOKEN_KEY = "token";

    @Override
    public void doProcess(TcRequest request, TcResponse response) {
        String token = (String) request.getData().get("token");
        if(StringUtils.isEmpty(token) || !"A123456".equals(token)){
            throw new GatewayException("401","鉴权失败");
        }
    }
}
