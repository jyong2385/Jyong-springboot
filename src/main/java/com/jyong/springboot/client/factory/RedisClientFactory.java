package com.jyong.springboot.client.factory;

import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @Author jyong
 * @Date 2024/5/31 14:23
 * @desc
 */

public class RedisClientFactory implements PooledObjectFactory<Jedis> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClientFactory.class);


    private String ip;

    private int port;

    public RedisClientFactory(String ip,int port){
        this.ip = ip;
        this.port = port;
    }


    /**
     *  这里才是创建客户端的
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<Jedis> makeObject() throws Exception {
        Jedis jedis = new Jedis(ip, port);
        return new DefaultPooledObject<>(jedis);
    }


    /**
     * 销毁池子中的对象
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Jedis> pooledObject) throws Exception {
        LOGGER.info("jedis destroyObject ...");
    }

    /**
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<Jedis> pooledObject) throws Exception {

    }



    /**
     * @param p
     * @param destroyMode
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Jedis> p, DestroyMode destroyMode) throws Exception {
        PooledObjectFactory.super.destroyObject(p, destroyMode);
    }



    /**
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<Jedis> pooledObject) throws Exception {

    }

    /**
     * @param pooledObject
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Jedis> pooledObject) {
        return false;
    }
}
