package com.jyong.springboot.web.controller;

import com.jyong.springboot.web.Result;
import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author jyong
 * @Date 2024/5/4 12:21
 * @desc
 */

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/query.json")
    Result<List<User>> queryAllUser() {
        Result<List<User>> result = new Result<>();
        result.setSuccess(true);
        result.setData(userService.selectAll());
        return result;
    }

    @GetMapping("/queryByName.json")
    Result<List<User>> queryByName(@RequestParam String name) {
        Result<List<User>> result = new Result<>();
        result.setSuccess(true);
        result.setData(userService.selectByName(name));
        return result;
    }

}
