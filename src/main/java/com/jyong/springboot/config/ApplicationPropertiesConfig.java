package com.jyong.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(value = "application.properties")
@Data
public class ApplicationPropertiesConfig {

    @Value("${redis.ip}")
    private String redisIp;


    @Value("${redis.port}")
    private String redisPort;

}
