package com.jyong.springboot.service;

import com.jyong.springboot.dao.model.User;

import java.util.List;

/**
 * @Author jyong
 * @Date 2024/5/4 12:23
 * @desc
 */

public interface UserService {

    List<User> selectAll();

    List<User> selectByName(String name);

}
