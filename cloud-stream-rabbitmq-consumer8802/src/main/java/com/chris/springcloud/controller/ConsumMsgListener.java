package com.chris.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
@Slf4j
public class ConsumMsgListener {

    @Value("${server.port}")
    private String serverPort;

    //监听队列，用于消费者的队列的消息接收
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        log.info("消费者1号,接收到的消息------>" + message.getPayload() + ", port: " + serverPort);
    }

}

