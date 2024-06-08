package com.jyong.springboot.web.vo;

import lombok.Data;

import java.util.Map;

/**
 * @Author jyong
 * @Date 2024/5/3 10:28
 * @desc
 */

@Data
public class IndexTemplateRequest {

    private String name;
    private Map<String,Object> mapping;

}
