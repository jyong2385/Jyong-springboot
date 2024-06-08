package com.jyong.springboot.web;

import com.jyong.springboot.web.Result;

/**
 * @Author jyong
 * @Date 2024/5/3 10:32
 * @desc
 */

public class ResultUtils<T> {

    public static Result<Object> ok(Object data){
        Result result = new Result();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }


}
