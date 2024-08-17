package com.jyong.springboot.web.controller;

import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.entity.UserRequest;
import com.jyong.springboot.service.UserService;
import com.jyong.springboot.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户注册接口
     */
    @PostMapping(value = "/register.json")
    Result<Long> register(UserRequest user) {
        Result<Long> result = new Result<>();
        try {
            result.setSuccess(true);
            result.setData(userService.register(user));
        }catch (Exception ex){
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @GetMapping("/query.json")
    Result<List<User>> queryAllUser() {
        Result<List<User>> result = new Result<>();
        try {
            result.setSuccess(true);
            result.setData(userService.selectAll());
        }catch (Exception ex){
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @GetMapping("/queryByName.json")
    Result<List<User>> queryByName(@RequestParam String name) {
        Result<List<User>> result = new Result<>();
        result.setSuccess(true);
        result.setData(userService.selectByName(name));
        return result;
    }

    @GetMapping("/selectByUpdateDate.json")
    Result<List<User>> selectByUpdateDate(@RequestParam String startDate,@RequestParam String endDate) {
        Result<List<User>> result = new Result<>();
        result.setSuccess(true);
        result.setData(userService.selectByUpdateDate(startDate,endDate));
        return result;
    }

}
