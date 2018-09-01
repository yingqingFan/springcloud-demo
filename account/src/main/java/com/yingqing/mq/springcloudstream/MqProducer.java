package com.yingqing.mq.springcloudstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MyProcessor.class)
public class MqProducer {

    @Autowired
    private ApplicationContext applicationContext;


    public void send(String message) {
        MessageChannel channel = (MessageChannel) applicationContext.getBean(MyProcessor.OUTPUT);
        channel.send(MessageBuilder.withPayload(message).build());
    }
}
