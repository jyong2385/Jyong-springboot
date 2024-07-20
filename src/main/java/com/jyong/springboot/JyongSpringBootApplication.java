package com.jyong.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @Author jyong
 * @Date 2023/10/17 20:25
 * @desc
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.jyong.springboot.dao.mapper")
public class JyongSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JyongSpringBootApplication.class, args);
    }
}
