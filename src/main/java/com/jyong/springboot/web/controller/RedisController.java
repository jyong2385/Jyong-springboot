package com.jyong.springboot.web.controller;

import com.jyong.springboot.client.RedisClient;
import com.jyong.springboot.web.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author jyong
 * @Date 2024/6/1 10:02
 * @desc
 */

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisClient redisClient;


    @GetMapping("/getKey")
    @ResponseBody
    public Result<String> getKey(@RequestParam String key){
        Result<String> result = new Result<>();
        result.setData(redisClient.getKey(key));
        result.setSuccess(true);
        return result;
    }

    @GetMapping("/putData")
    @ResponseBody
    public Result<String> getKey(@RequestParam String key, @RequestParam String data){
        Result<String> result = new Result<>();
        try {
            result.setData(redisClient.putData(key,data));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
