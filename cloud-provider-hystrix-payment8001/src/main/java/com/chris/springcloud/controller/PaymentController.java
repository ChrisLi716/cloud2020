package com.chris.springcloud.controller;

import com.chris.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/payment/hystrix")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") int id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("result:" + result);
        return result;
    }

    @GetMapping("/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") int id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("result:" + result);
        return result;
    }


    /**
     * 模拟服务熔断
     */
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") int id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("paymentCircuitBreaker, result: " + result);
        return result;
    }
}
