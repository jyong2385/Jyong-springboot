package com.jyong.springboot.client.pool;

import com.jyong.springboot.client.factory.RedisClientFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @Author jyong
 * @Date 2024/5/31 14:35
 * @desc
 */

@Component
public class RedisClientPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClientPool.class);

    private static GenericObjectPool<Jedis> pool;


    /**
     * 初始化redis客户端
     */
    public void init(String ip, int port) {
        //要池化的对象的工厂类,这个是要实现的类
        RedisClientFactory redisClientFactory = new RedisClientFactory(ip, port);

        //利用对象工厂类和配置类生成对象类
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
        //池子中存放的对象数量
        poolConfig.setMaxTotal(5);
        pool = new GenericObjectPool<>(redisClientFactory, poolConfig);
    }


    /**
     * 获取对象
     */
    public Jedis getClient() {
        //从池子中获取对象
        Jedis jedis = null;
        try {
            jedis = pool.borrowObject();
        } catch (Exception e) {
            LOGGER.error("getClient jedis error !!! ", e);
        }
        return jedis;

    }


    /**
     * 归还对象
     * @param jedis
     */
    public void returnClient(Jedis jedis){
        if(jedis != null){
            try {
                pool.returnObject(jedis);
            }catch (Exception e){
                LOGGER.error("returnClient jedis error !!! ",e);
            }
        }
    }

    /**
     * 释放
     */
    public void close(){
        try {
            //等待所有线程归还对象并不再获取新的对象
            pool.clear();;
            //关闭对象池
            pool.close();
        }catch (Exception e){
            LOGGER.error("close jedis error !!! ",e);
        }


    }


}
