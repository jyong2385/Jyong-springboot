package com.jyong.springboot.Util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.LoggerFactory;

/**
 * @Author jyong
 * @Date 2024/5/18 16:47
 * @desc
 */

public class LogUtil {


    public static void info(Class clazz, String... message) {
        LoggerFactory.getLogger(clazz).info(msg(message));
    }

    public static void warn(Class clazz, String... message) {
        LoggerFactory.getLogger(clazz).warn(msg(message));
    }

    public static void error(Class clazz, Throwable e, String... message) {
        LoggerFactory.getLogger(clazz).error(msg(message), e);
    }

    private static String msg(String... message) {
        return StrUtil.join(" ", message);
    }


}
