package com.jyong.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author jyong
 * @Date 2024/5/2 21:37
 * @desc
 */

@Data
@Getter
@Setter
public class Person implements Serializable {

    private String name;
    private int age;
    private String address;
    private Date birthday;

}
