package com.jyong.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author jyong
 * @Date 2024/6/1 22:15
 * @desc
 */
@Component
@RefreshScope
@Data
public class CommonNacosConfig {


    @Value("${redis.ip}")
    private String redisIp;


    @Value("${redis.port}")
    private String redisPort;

    @Value("${common.value}")
    private String common;

}
