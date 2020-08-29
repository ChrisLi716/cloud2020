package com.chris.springcloud.controller;

import com.chris.springcloud.entities.CommonResult;
import com.chris.springcloud.entities.Payment;
import com.chris.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer/payment")
public class OrderFeignController {

    @Resource
    public PaymentFeignService paymentFeignService;


    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/feign/timeout")
    public String paymentFeighTimeout() {
        // openfeign-ribbon 客户端默认等待1秒钟
        return paymentFeignService.paymentFeighTimeout();
    }
}
