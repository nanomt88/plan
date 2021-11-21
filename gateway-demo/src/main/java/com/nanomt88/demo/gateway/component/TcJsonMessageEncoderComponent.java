package com.nanomt88.demo.gateway.component;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TcJsonMessageEncoderComponent implements ITcComponent{

    /**
     * 组件处理逻辑
     * @param request
     * @param response
     */
   public void doProcess(TcRequest request, TcResponse response){
       log.info("invoke response:[{}]" , request, response);
       String jsonString = JSONObject.toJSONString(response.getData());
       response.setOriginResponseString(jsonString);
       response.setOriginResponseBody(jsonString.getBytes(response.getCharset()));
   }
}
