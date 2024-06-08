package com.jyong.springboot.enums;

/**
 * @Author jyong
 * @Date 2023/10/17 20:48
 * @desc
 */

public enum ProcessEnumType {

    PROCESS_A,

    PROCESS_B;

    public static String getType(String type){
        for (ProcessEnumType value : ProcessEnumType.values()) {
            if(value.name().equalsIgnoreCase(type)){
                return value.name();
            }
        }
        return null;
    }
    public static ProcessEnumType getEnumType(String type){
        for (ProcessEnumType value : ProcessEnumType.values()) {
            if(value.name().equalsIgnoreCase(type)){
                return value;
            }
        }
        return null;
    }
}
