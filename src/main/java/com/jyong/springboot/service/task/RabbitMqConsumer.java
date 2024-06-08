package com.jyong.springboot.service.task;


import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.jyong.springboot.config.JmsConfig;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMqConsumer {

    private Logger log = LoggerFactory.getLogger(RabbitMqConsumer.class);

    //消费者实体对象

    private DefaultMQPushConsumer consumer;

    //消费者组

    public static final String consumer_group="test_consumer";


    //通过构造器，创建对象
    public RabbitMqConsumer() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumer_group);
        consumer.setNamesrvAddr(JmsConfig.name_server);
        //消费模式：一个新的订阅组第一次启动从队列的最后位置开始消费，后续再启动接着上次消费的位置开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //订阅主题和标签  * ：代表所有标签下的数据
        consumer.subscribe(JmsConfig.topic,"*");

        //注册消费的监听并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener(new MessageListenerConcurrently() {


            @SneakyThrows
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                for (MessageExt messageExt : list) {
                //消费者获取消息
                    try {
                        String s = new String(messageExt.getBody(), "utf-8");
                        log.info("consumer-获取消息-主题topic为={},消费消息为={},",messageExt.getTopic(),s);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        System.out.println("==========RabbitMq消费者 启动成功============");
    }


}
