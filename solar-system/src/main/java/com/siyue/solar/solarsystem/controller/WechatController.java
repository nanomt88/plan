package com.siyue.solar.solarsystem.controller;

import com.siyue.solar.solarsystem.entity.User;
import com.siyue.solar.solarsystem.service.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author nanomt88@gmail.com
 * @create 2019-01-06 12:05
 **/
@RestController
public class WechatController {

    private Logger logger = LoggerFactory.getLogger(WechatController.class);


    private WechatService wechatService;

    @Autowired
    public WechatController(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    @GetMapping("/toLogin")
    public ModelAndView toLogin(HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        // 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("toLogin.html");

        return view;
    }

    @GetMapping("/showQrcode")
    public void showQrcode(HttpServletRequest request, HttpServletResponse response) {

        File file = wechatService.showLoginQrcode(request.getSession().getId());

        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();

            if(file==null || !file.exists()){
                return ;
            }
            byte[] buffer = new byte[1024];
            inputStream = new FileInputStream(file);
            int length = 0;
            while ( (length = inputStream.read(buffer)) != -1){
                response.getOutputStream().write(buffer, 0 , length);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("异常信息是:",e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("异常信息是:",e);
            }
        }

//        view.addObject("author", all);
    }
}
