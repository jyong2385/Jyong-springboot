package com.jyong.springboot.web;

import com.jyong.springboot.Util.UserContextUtil;
import com.jyong.springboot.dao.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author jyong
 * @Date 2024/5/2 17:42
 * @desc
 */


@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4016057333789422696L;
    /**
     * 返回状态
     */
    private boolean success = false;

    /**
     * 返回信息
     */
    private String message;


    /**
     * 返回数据
     */
    private T data;

    private String traceId;

    public static Result<Object> successResult(Object data) {
        return new Result<>(data, true);
    }

    public Result() {
        this.traceId = UserContextUtil.getTraceId();
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.traceId = UserContextUtil.getTraceId();
    }

    public Result(T data, boolean success) {
        this.data = data;
        this.success = success;
        this.traceId = UserContextUtil.getTraceId();
    }

}
