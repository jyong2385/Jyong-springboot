package com.jyong.springboot.client;

import com.jyong.springboot.client.pool.RedisClientPool;
import com.jyong.springboot.config.ApplicationPropertiesConfig;
import com.jyong.springboot.config.CommonPropertiesConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author jyong
 * @Date 2024/6/1 09:51
 * @desc
 */

@Component
public class RedisClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);

    @Resource
    private RedisClientPool redisClientPool;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @PostConstruct
    public void init(){
        String redisIp = applicationPropertiesConfig.getRedisIp();
        String redisPort = applicationPropertiesConfig.getRedisPort();
        LOGGER.info("start init redis client !!! ,redisIp="+redisIp+" ,redisPort="+redisPort);
        redisClientPool.init(redisIp, Integer.parseInt(redisPort));
    }

    public String getKey(String key){
        Assert.isTrue(StringUtils.isNotBlank(key),"key is null");
        Jedis client = null;
        try {
            client = redisClientPool.getClient();
            return client.get(key);
        }catch (Exception e){
            LOGGER.error("get redis data error !!!",e);
        }finally {
            redisClientPool.returnClient(client);
        }
        return null;
    }


    /**
     *
     * @param key
     * @param data
     * @param seconds 过期事件秒
     * @return
     */
    public String putData(String key,String data,long seconds) {
        Assert.isTrue(StringUtils.isNotBlank(key),"key is null");
        Assert.isTrue(StringUtils.isNotBlank(data),"data is null");
        Jedis client = null;
        try {
            SetParams setParams = new SetParams();
            client = redisClientPool.getClient();
            setParams.ex(seconds);
            return client.set(key,data,setParams);
        }catch (Exception e){
            LOGGER.error("put redis data error !!!",e);
        }finally {
            redisClientPool.returnClient(client);
        }
        return null;
    }
}
