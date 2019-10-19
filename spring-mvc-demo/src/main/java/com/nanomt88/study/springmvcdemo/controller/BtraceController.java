package com.nanomt88.study.springmvcdemo.controller;

import com.nanomt88.study.springmvcdemo.dto.UserDTO;
import com.nanomt88.study.springmvcdemo.service.BraceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

/**
 *  brace test
 * @author nanomt88@gmail.com
 * @date 18.11.18 10:24
 * @updateLog:
 *      TODO  update by nanomt88@gmail.com, 18.11.18 10:24
 */
@RestController
public class BtraceController {
    private static final Logger log = LoggerFactory.getLogger(BtraceController.class);
    @Autowired
    BraceService braceService;

    @PostMapping("/demo/test")
        public Map<Integer, Object> test(@RequestBody  UserDTO user){
        method1();
        method2();
        method3();
        return braceService.insert(user);
    }

    private void method1(){
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("method1");
    }

    private void method2(){
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("method2");
    }

    private void method3(){
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("method3");
    }
}
