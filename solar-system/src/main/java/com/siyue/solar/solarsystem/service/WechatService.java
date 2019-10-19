package com.siyue.solar.solarsystem.service;


import java.io.File;

/**
 * 操作微信登录类
 *
 * @author nanomt88@gmail.com
 * @create 2019-01-06 12:06
 **/

public interface WechatService {

    /**
     * 显示 微信登录二维码
     *
     * @return
     */
    File showLoginQrcode(String userId);

    /**
     * 检查登录状态
     * @param userId
     * @return
     */
    boolean checkLogin(String userId);
}
