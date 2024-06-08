package com.jyong.springboot.web;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author jyong
 * @Date 2024/5/2 17:42
 * @desc
 */


@Data
public class Result<T> implements Serializable {

    /**
     * 返回状态
     */
    private boolean success = false ;

    /**
     * 返回信息
     */
    private String message;


    /**
     * 返回数据
     */
    private T data;
}
