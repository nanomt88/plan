package com.siyue.solar.solarsystem.service.impl;

import com.siyue.solar.solarsystem.service.WechatService;
import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.utils.MD5Checksum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author nanomt88@gmail.com
 * @create 2019-01-06 13:22
 * 18666826618
 **/
@Service
public class WechatServiceImpl implements WechatService {

    private Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    private ConcurrentMap<String, WeChatBot> wechatUserMap = new ConcurrentHashMap<>();

    @Override
    public File showLoginQrcode(final String userId) {

        if (!wechatUserMap.containsKey(userId)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Config config = Config.load("/wechat.properties");
                    config.autoLogin(false);
                    config.showTerminal(true);
//                  config.assetsDir(uuid);
                    WeChatBot.Builder build = new WeChatBot.Builder();
                    build.config(config);

                    WeChatBot bot = build.build();
                    wechatUserMap.put(userId, bot);
                    //启动微信机器人
                    bot.start();
//                  bot.api().login(false);
                }
            }).start();

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        WeChatBot bot = wechatUserMap.get(userId);
        File file = new File( bot.config().assetsDir(), "qrcode.png");
//        String checksum = MD5Checksum.getMD5Checksum(file.getAbsolutePath());
//        wechatUserMap.put(checksum, bot);

        return file;
    }

    @Override
    public boolean checkLogin(String userId) {

        if (!wechatUserMap.containsKey(userId)) {
            return false;
        }
        WeChatBot bot = wechatUserMap.get(userId);
        if(bot.isRunning()){
            logger.info("session ID：[{}] 登录微信成功", userId);
            return true;
        }
        logger.info("session ID：[{}] 登录微信确认中...", userId);
        return false;
    }
}
