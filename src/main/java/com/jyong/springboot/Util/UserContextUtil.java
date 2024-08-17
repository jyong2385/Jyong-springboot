package com.jyong.springboot.Util;

import com.jyong.springboot.enums.UserInfo;

import java.util.Optional;

/**
 * @author Jyong
 * @date 2024/8/17 10:43
 * @description
 */
public class UserContextUtil {


    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();


    public static void setUserInfo(UserInfo userInfo) {
        threadLocal.set(userInfo);
    }

    public static void setUserInfo(Long userId,String username) {
        UserInfo userInfo = Optional.ofNullable(threadLocal.get()).orElse(new UserInfo());
        userInfo.setUserId(userId);
        userInfo.setUsername(username);
        threadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return threadLocal.get();
    }
    public static String getUserName() {
        return Optional.ofNullable(threadLocal.get()).orElse(new UserInfo()).getUsername();
    }

    public static Long getUserId() {
        return Optional.ofNullable(threadLocal.get()).orElse(new UserInfo()).getUserId();
    }

    public static void clear(){
        threadLocal.remove();
    }

    public static String getTraceId(){
        return Optional.ofNullable(threadLocal.get()).orElse(new UserInfo()).getTraceId();
    }
    public static void setTraceId(String traceId){
        UserInfo userInfo = Optional.ofNullable(threadLocal.get()).orElse(new UserInfo());
        userInfo.setTraceId(traceId);
        threadLocal.set(userInfo);
    }
}
