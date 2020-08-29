package com.chris.springcloud.service;

import com.chris.springcloud.entities.CommonResult;
import com.chris.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PaymentFeignService 定义了服务提供者的服务接口[PaymentController]原样拷贝，URL,方法都需要一样
 * 返回值因为是面向客户端所以需要返回CommonResult
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
@RequestMapping("/api/payment")
public interface PaymentFeignService {

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/feign/timeout")
    public String paymentFeighTimeout();

}
