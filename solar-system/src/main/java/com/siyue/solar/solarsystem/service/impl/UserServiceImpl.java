package com.siyue.solar.solarsystem.service.impl;

import com.siyue.solar.solarsystem.entity.User;
import com.siyue.solar.solarsystem.repository.UserRepository;
import com.siyue.solar.solarsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author nanomt88@gmail.com
 * @create 2019-01-04 21:58
 **/
@Service
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> save(User user) {
        User save = userRepository.save(user);
        log.info("入库：[{}]", save);
        return findAll();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> delete(Long id) {
        log.info("删除ID：[{}]" , id);
        userRepository.deleteById(id);
        return userRepository.findAll();
    }

    @Override
    public List<User> update(User user) {
        log.info("更新：[{}]" , user);
        User user1 = userRepository.findById(user.getId()).get();
        if(user1 != null){
            BeanUtils.copyProperties(user, user1);
            userRepository.save(user1);
        }
        return userRepository.findAll();
    }
}
