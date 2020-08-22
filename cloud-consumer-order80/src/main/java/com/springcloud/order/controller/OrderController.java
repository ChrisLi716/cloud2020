package com.springcloud.order.controller;

import com.chris.springcloud.entities.CommonResult;
import com.chris.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j

@RequestMapping("/consumer")
public class OrderController {


    // 单机版本
    //public static final String PAYMENT_URL = "http://localhost:8001";

    // 集群版本
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("/payment/create")
    @SuppressWarnings("unchecked")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/api/payment/create", payment, CommonResult.class);
    }


    @GetMapping("/payment/get/{id}")
    @SuppressWarnings("unchecked")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/api/payment/get/" + id, CommonResult.class);
    }

}
