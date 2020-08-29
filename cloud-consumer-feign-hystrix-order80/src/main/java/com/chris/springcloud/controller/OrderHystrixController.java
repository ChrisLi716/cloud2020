package com.chris.springcloud.controller;


import com.chris.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer/payment")
@DefaultProperties(defaultFallback = "paymentGlobalFallBack")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;


    @GetMapping("/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") int id) {
        return paymentHystrixService.paymentInfo_Ok(id);
    }

    @GetMapping("/timeout/{id}")
    @HystrixCommand(fallbackMethod = "timeOutHandler", commandProperties = {@HystrixProperty(name =
            "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    public String paymentInfo_TimeOut(@PathVariable("id") int id) {
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    @SuppressWarnings("unused")
    private String timeOutHandler(int id) {
        return "I'm order80, the service provider system busy or server callapse, id:" + id;
    }


    @GetMapping("/timeout2/{id}")
    @HystrixCommand()
    public String paymentInfo_TimeOut2(@PathVariable("id") int id) {
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    /**
     * 此方法为服级降级的全局默认FallBack调用方法
     *
     * @return fallback message
     */
    @SuppressWarnings("unused")
    private String paymentGlobalFallBack() {
        return "system is busy , pls try later!";
    }

}
