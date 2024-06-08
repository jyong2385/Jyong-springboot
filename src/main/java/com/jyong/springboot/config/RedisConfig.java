package com.jyong.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author jyong
 * @Date 2024/6/1 09:54
 * @desc
 */


public class RedisConfig {


    @Value("redis.ip")
    private String ip;

    @Value("redis.port")
    private int port;


    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
