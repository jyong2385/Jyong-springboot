package com.jyong.springboot.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.jyong.springboot.client.RedisClient;
import com.jyong.springboot.enums.DeleteEnum;
import com.jyong.springboot.dao.mapper.UserMapper;
import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.dao.model.UserExample;
import com.jyong.springboot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author jyong
 * @Date 2024/5/4 12:23
 * @desc
 */
@Service
public class UserServiceImpl implements UserService {

    private String USER_CACHE_PREFIX = "user:";

    @Resource
    private RedisClient redisClient;

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

    @Override
    public Long register(User user) {
        Assert.notNull(user.getName(), "用户吗为空");
        //注册名字不能重复
        UserExample example = new UserExample();
        example.createCriteria().andIsDeletedEqualTo(DeleteEnum.UN_DELETE.getCode()).andNameEqualTo(user.getName());
        List<User> users = userMapper.selectByExample(example);
        Assert.isTrue(CollectionUtils.isEmpty(users), "用户名已存在 !!!");


        User record = new User();
        record.setName(user.getName());
        record.setEmail(user.getEmail());
        record.setPhone(user.getPhone());
        record.setAge(user.getAge());
        record.setIsDeleted(DeleteEnum.UN_DELETE.getCode());
        record.setSchool(user.getSchool());
        record.setAddress(user.getAddress());
        record.setBirthday(user.getBirthday());
        long i = userMapper.insert(record);
        return i > 0 ? record.getId() : null;
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByIdInCache(Long id) {
        String key = USER_CACHE_PREFIX + id;
        String value = redisClient.getKey(key);
        if (StringUtils.isNotBlank(value) && JSONUtil.isJson(value)) {
            return JSONUtil.toBean(value, User.class);
        } else {
            redisClient.putData(key, JSONUtil.toJsonStr(userMapper.selectByPrimaryKey(id)), 60 * 60);
        }
        return null;
    }
}

