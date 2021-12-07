package com.nanomt88.demo.dubbo.generic;

import com.nanomt88.demo.dubbo.DubboConsumerClientFactory;
import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nanomt88@gmail.com
 * @create 2021-11-28 22:44
 **/
@RestController
@RequestMapping(path = "/api")
@Slf4j
public class MyGenericController {

    @Autowired
    private IGenericService genericService;

    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=127.0.0.1
     */
    @RequestMapping(value = "/test")
    public Map<String,Object> test(@RequestBody Map data) {
        log.info("请求参数： [{}]" ,data);
        try {
//            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode","00000");
        map.put("data", data);
        return map;
    }


    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=127.0.0.1
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String,Object> invokeUser(@RequestParam(value = "timeout", required = false) String time) {
        User user = new User();
        user.setId("100");
        user.setName("张三");
        user.setAccount("zhangshan102");

        DubboConsumerClientFactory clientFactory = DubboConsumerClientFactory.getInstance();

        if(!StringUtils.isEmpty(time)) {
            clientFactory.changeTimeout(IMyService.class.getName(), Integer.parseInt(time));
        }
        Object getUser = clientFactory.genericInvoke(IMyService.class.getName(), "getUser", user);
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode","00000");
        map.put("user",getUser);
        return map;
    }


    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=127.0.0.1
     */
    @RequestMapping(value = "/virtualApi", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String,Object> invokeVirtualApi(@RequestBody User user) {
        log.info("入参：[{}]",user);
        DubboConsumerClientFactory clientFactory = DubboConsumerClientFactory.getInstance();
        RpcContext.getContext().setAttachment("apiCode", "getOrderByUser");
        Object getUser = clientFactory.genericInvoke("com.nanomt88.demo.dubbo.virtual.IVirtualApiService",
                "getOrderByUser", user);
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode","00000");
        map.put("user",getUser);
        return map;
    }

}
