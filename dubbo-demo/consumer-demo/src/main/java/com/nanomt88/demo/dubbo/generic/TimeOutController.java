package com.nanomt88.demo.dubbo.generic;

import com.nanomt88.demo.dubbo.sample.IMiddleService;
import com.nanomt88.demo.dubbo.sample.Order;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class TimeOutController {

    @Autowired
    private IMiddleService middleService;

    @RequestMapping(value = "/middle", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String,Object> invokeMiddleService(@RequestBody User user) {
        log.info("入参：[{}]",user);
        RpcContext.getContext().setObjectAttachment("timeout", 12000+"");
        Order order = middleService.middleRpc(user);
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode","00000");
        map.put("user",order);
        return map;
    }
}
