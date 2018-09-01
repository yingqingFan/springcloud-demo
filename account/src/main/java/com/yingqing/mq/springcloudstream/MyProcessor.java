package com.yingqing.mq.springcloudstream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

public interface MyProcessor {
    String INPUT = "myInput";

    String OUTPUT = "myOutput";

    @Input(INPUT)
    SubscribableChannel myInput();

    @Output(OUTPUT)
    MessageChannel anOutput();


}
