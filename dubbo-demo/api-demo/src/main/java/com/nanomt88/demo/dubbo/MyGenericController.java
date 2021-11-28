package com.nanomt88.demo.dubbo;

import com.nanomt88.demo.dubbo.generic.IGenericService;
import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.User;
import org.apache.dubbo.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nanomt88@gmail.com
 * @create 2021-11-28 22:44
 **/
@Controller
@RequestMapping(path = "/api")
public class MyGenericController {

    @Autowired
    private IGenericService genericService;


    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=192.168.1.112
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    void invokeUser() {
        User user = new User();
        user.setId("100");
        user.setName("张三");
        user.setAccount("zhangshan102");
        genericService.invokeUser(user);
    }

}
