package com.chris.springcloud.controller;

import com.chris.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMsgController {

    @Resource
    private IMessageProvider messageProvider;

    @GetMapping("/sendMsg")
    public String sendMessage(){
        return messageProvider.send();
    }

}
