package com.nanomt88.demo.gateway.component;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcJsonMessageDecoderComponent implements ITcComponent{

    /**
     * 组件处理逻辑
     * @param request
     * @param response
     */
   public void doProcess(TcRequest request, TcResponse response){
       log.info("invoke , request:[{}]" , request);
       String originRequestString = request.getOriginRequestString();
       JSONObject json = JSON.parseObject(originRequestString);
       request.setData(json);
   }
}
