package com.jyong.springboot.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jyong.springboot.enums.DeleteEnum;
import com.jyong.springboot.dao.mapper.UserMapper;
import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.dao.model.UserExample;
import com.jyong.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author jyong
 * @Date 2024/5/4 12:23
 * @desc
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @return
     */
    @Override
    public List<User> selectAll() {
        UserExample example = new UserExample();
        example.createCriteria().andIsDeletedEqualTo(DeleteEnum.UN_DELETE.getCode());
        return userMapper.selectByExample(example);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public List<User> selectByName(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andIsDeletedEqualTo(DeleteEnum.UN_DELETE.getCode()).andNameLike("%" + name + "%");
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> selectByUpdateDate(String startDate, String endDate) {

        DateTime start = DateUtil.parse(startDate, DatePattern.NORM_DATETIME_PATTERN);
        DateTime end = DateUtil.parse(endDate, DatePattern.NORM_DATETIME_PATTERN);

        UserExample example = new UserExample();
        example.createCriteria().andIsDeletedEqualTo(DeleteEnum.UN_DELETE.getCode()).andUpdateTimeGreaterThanOrEqualTo(start).andUpdateTimeLessThanOrEqualTo(end);
        return userMapper.selectByExample(example);
    }
}

