package com.chris.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallBackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_Ok(int id) {
        return "PaymentFallBackService fallback-paymentInfo_Ok, the provider collapsed, try it later.";
    }

    @Override
    public String paymentInfo_TimeOut(int id) {
        return "PaymentFallBackService fallback-paymentInfo_TimeOut, the provider collLapsed, try it later.";
    }
}
