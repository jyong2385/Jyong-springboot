package com.jyong.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(value = "common.properties")
public class CommonPropertiesConfig {

}
