package com.jyong.springboot.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.jyong.springboot.dao.model.User;

/**
 * @author Jyong
 * @date 2024/8/17 14:38
 * @description
 */
public class UserRequest extends User {

    private String birth;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
        setBirthday(DateUtil.parse(birth, DatePattern.NORM_DATETIME_PATTERN));
    }
}
