package com.jyong.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.convert.Delimiter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private Integer age;
    private String address;
    private Date birthday;
    private List<String> names;

    public void setNames(String name) {
        this.names = Arrays.asList(name.split(","));
    }
}
