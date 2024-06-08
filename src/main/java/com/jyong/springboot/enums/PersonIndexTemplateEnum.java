package com.jyong.springboot.enums;

/**
 * @Author jyong
 * @Date 2024/5/2 21:34
 * @desc
 */

public enum PersonIndexTemplateEnum {


    NAME("name","名字"),

    AGE("age","年龄"),

    ADDRESS("address","地址");


    private String esDataKey;
    private String desc;

    PersonIndexTemplateEnum(String esDataKey,String desc){
        this.desc= desc;
        this.esDataKey=esDataKey;
    }

    public String getEsDataKey() {
        return esDataKey;
    }

    public void setEsDataKey(String esDataKey) {
        this.esDataKey = esDataKey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
