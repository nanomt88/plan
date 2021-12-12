package com.nanomt88.demo.dubbo;

import com.nanomt88.demo.dubbo.sample.IConsistentHashService;
import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.Order;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author nanomt88@gmail.com
 * @create 2021-11-28 22:44
 **/
@RestController
@RequestMapping(path = "/api")
@Slf4j
public class MyController {

    @Autowired
    private IConsistentHashService myService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public Map<String,Object> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode","00000");
        return map;
    }

    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=127.0.0.1
     */
    @RequestMapping(value = "/asyncTest", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String,Object> asyncTest(@RequestBody User user) {
        log.info("请求参数： [{}]" ,user);
        try {
//            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        if(user == null || StringUtils.isEmpty(user.getId())){
            map.put("responseCode","999999");
            return map;
        }
        user.setRequestNo(UUID.randomUUID().toString());
        RpcContext.getContext().setObjectAttachment("timeout", 12000+"");
        Order hello = myService.asyncToSync(user.getRequestNo(),user);

        map.put("responseCode","00000");
        map.put("data", hello);
//        map.put("responseMessage" , hello);
        return map;
    }
}
