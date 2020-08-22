package com.chris.spirngcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderZKController {

    //cloud-provider-payment 为在zookeeper中注册的znode节点名称
    private static final String PAYMENT_URL = "http://cloud-provider-payment";


    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/zk")
    public String paymentInfo() {
        return restTemplate.getForObject(PAYMENT_URL + "/api/payment/zk", String.class);
    }


}
