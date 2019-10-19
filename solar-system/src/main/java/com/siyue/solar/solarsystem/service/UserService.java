package com.siyue.solar.solarsystem.service;

import com.siyue.solar.solarsystem.entity.User;

import java.util.List;

/**
 * demo
 *
 * @author nanomt88@gmail.com
 * @create 2019-01-04 21:15
 **/
public interface UserService {

    List<User> save(User user);

    List<User> findAll();

    List<User> delete(Long id);

    List<User> update(User user);

}
