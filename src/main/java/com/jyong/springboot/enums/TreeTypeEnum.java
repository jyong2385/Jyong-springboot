package com.jyong.springboot.enums;

import com.github.mustachejava.Code;

/**
 * @author Jyong
 * @date 2024/7/28 12:25
 * @description
 */
public enum TreeTypeEnum {

    /**
     * 类目
     */
    CATEGORY("CATEGORY","类目"),

    /**
     *标签
     */
    TAG("TAG","标签");


    public String code;
    public String desc;

    TreeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
