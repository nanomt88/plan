package com.siyue.solar.solarsystem.controller;

import com.siyue.solar.solarsystem.entity.User;
import com.siyue.solar.solarsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * hello world
 *
 * @author nanomt88@gmail.com
 * @create 2018-12-25 7:04
 **/

@RestController
public class UserDemoController {

    private Logger logger = LoggerFactory.getLogger(UserDemoController.class);

    private UserService userService;

    @Autowired
    public UserDemoController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ModelAndView save(@RequestBody User user) {
        logger.info("入参：[{}]", user);
        ModelAndView view = new ModelAndView();
        // 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("userDemo");
        // 设置属性
        view.addObject("title", "我的第一个WEB页面 - POST");
        view.addObject("desc", "欢迎进入 solar 系统");

        //  以下方法取值方式一样
        // HttpServletRequest request
//        request.setAttribute("title", "我的第一个WEB页面");
//        request.setAttribute("desc", "欢迎进入battcn-web 系统");

        List<User> all = userService.save(user);
        view.addObject("author", all);
        return view;
    }

    @GetMapping("/user")
    public ModelAndView findAll() {
        ModelAndView view = new ModelAndView();
        // 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("userDemo");
        // 设置属性
        view.addObject("title", "我的第一个WEB页面 - GET");
        view.addObject("desc", "欢迎进入 solar 系统");

        List<User> all = userService.findAll();
        view.addObject("author", all);
        return view;
    }

    @DeleteMapping("/user/{id}")
    public ModelAndView delete(@PathVariable("id") Long id){
        ModelAndView view = new ModelAndView();
        // 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("userDemo");
        // 设置属性
        view.addObject("title", "我的第一个WEB页面 - DELETE");
        view.addObject("desc", "欢迎进入 solar 系统");

        List<User> all = userService.delete(id);
        view.addObject("author", all);
        return view;
    }

    @PutMapping("/user")
    public ModelAndView update(@RequestBody User user){
        ModelAndView view = new ModelAndView();
        // 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("userDemo");
        // 设置属性
        view.addObject("title", "我的第一个WEB页面 - PUT");
        view.addObject("desc", "欢迎进入 solar 系统");

        List<User> all = userService.update(user);
        view.addObject("author", all);
        return view;
    }


//    @GetMapping("/index1")
//    public String index1(HttpServletRequest request) {
//        // TODO 与上面的写法不同，但是结果一致。
//        // 设置属性
//        request.setAttribute("title", "我的第一个WEB页面");
//        request.setAttribute("desc", "欢迎进入battcn-web 系统");
//        User author = new User();
//        author.setAge(22);
//        author.setEmail("1837307557@qq.com");
//        author.setName("唐亚峰");
//        request.setAttribute("author", author);
//        // 返回的 index 默认映射到 src/main/resources/templates/xxxx.html
//        return "index";
//    }
}
