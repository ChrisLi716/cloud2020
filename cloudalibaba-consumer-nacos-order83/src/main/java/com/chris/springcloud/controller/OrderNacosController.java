package com.chris.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderNacosController {

    @Value("${service-url.nacos-user-service}")
    private String PAYMENT_SERVICE_NAME;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("payment/nacos/{id}")
    public String getPaymentInfo(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_SERVICE_NAME + "/payment/nacos/" + id, String.class);
    }
}
