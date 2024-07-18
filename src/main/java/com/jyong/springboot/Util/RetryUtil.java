package com.jyong.springboot.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author Jyong
 * @date 2024/7/4 16:50
 * @description
 */
public class RetryUtil {


    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtil.class);


    public static <T> T execute(final Callable<T> callable,
                                final int retryTimes,
                                final long waitTimeInMilliSecond,
                                final double multiple , boolean throwException) {

        long waitTime = waitTimeInMilliSecond;
        Throwable cause = null;
        for (int i = 0; i <= retryTimes; i++) {

            try {
                return callable.call();
            } catch (Throwable e) {
                LOGGER.warn("execute call error ,", e.getCause());
                cause = e;
                try {
                    Thread.sleep(waitTime);
                } catch (Exception ex) {
                    LOGGER.warn("retry sleep error ,", e.getCause());
                }
                waitTime = (long) Math.ceil(waitTime * multiple);
            }

        }
        if(throwException){
            throw new RuntimeException(cause);
        }

        return null;
    }


}
