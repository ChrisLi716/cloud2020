package com.chris.springcloud.service.impl;

import cn.hutool.core.lang.UUID;
import com.chris.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

//定义消息的推送信道
@EnableBinding(Source.class)
@Slf4j
public class MessageProviderImpl implements IMessageProvider {


    //消息生产信道
    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String uuid = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(uuid).build());
        log.info("uuid:" + uuid);
        return uuid;
    }
}
