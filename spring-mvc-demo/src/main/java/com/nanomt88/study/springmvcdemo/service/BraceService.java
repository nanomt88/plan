package com.nanomt88.study.springmvcdemo.service;

import com.nanomt88.study.springmvcdemo.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.LockSupport;
/**
 *  brace 示例
 * @author nanomt88@gmail.com
 * @date 18.11.18 10:42
 * @updateLog:
 *       update by nanomt88@gmail.com, 18.11.18 10:42
 */
@Service
public class BraceService {

    private static final Logger log = LoggerFactory.getLogger(BraceService.class);


    private ConcurrentMap<Integer,Object> map = new ConcurrentHashMap<>();

    public Map<Integer,Object> insert(UserDTO user){

        LockSupport.parkNanos(100 * 1000L);

        test1();
        test2();
        test3();
        assert user!=null & user.getId()!=null;
        map.put(user.getId(), user);

        return map;
    }


    private void test1(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("test1");
    }

    private void test2(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("test2");

    }

    private void test3(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("test3");
    }
}
