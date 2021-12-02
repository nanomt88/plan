package com.nanomt88.demo.dubbo.generic;

import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nanomt88@gmail.com
 * @create 2021-11-28 22:44
 **/
@RestController
@RequestMapping(path = "/api")
public class MyGenericController {

    @Autowired
    private IGenericService genericService;

    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=127.0.0.1
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map<String,String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("responseCode","00000");
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

        DubboServcieClientFactory clientFactory = DubboServcieClientFactory.getInstance();

        if(!StringUtils.isEmpty(time)) {
            clientFactory.changeTimeout(IMyService.class.getName(), Integer.parseInt(time));
        }
        Object getUser = clientFactory.genericInvoke(IMyService.class.getName(), "getUser", user);
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode","00000");
        map.put("user",getUser);
        return map;
    }

}
