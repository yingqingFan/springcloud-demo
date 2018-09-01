package com.yingqing.mq.springcloudstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(MyProcessor.class)
public class MqReceiver {

    private static Logger log = LoggerFactory.getLogger(MqReceiver.class);

    @StreamListener(MyProcessor.INPUT)
    public void receiver(Message<String> message){
        log.debug("consumer start");
        String payload = message.getPayload();
        log.debug(payload);
    }
}
