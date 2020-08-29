package com.chris.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * PaymentFeignService 定义了服务提供者的服务接口[PaymentController]原样拷贝，URL,方法都需要一样
 */

@Component
//@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT")
//配置FeignClient,使用支持服务降级的fallback实现类
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallBackService.class, path = "/api/payment/hystrix")
public interface PaymentHystrixService {

    @GetMapping("/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") int id);

    @GetMapping("/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") int id);
}
