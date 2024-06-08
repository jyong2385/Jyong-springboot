package com.jyong.springboot.enums;

/**
 * @Author jyong
 * @Date 2024/5/4 12:08
 * @desc
 */

public enum DeleteEnum {

    DELETE("1", "删除"),

    UN_DELETE("0", "未删除");

    private String code;

    private String desc;

    DeleteEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
