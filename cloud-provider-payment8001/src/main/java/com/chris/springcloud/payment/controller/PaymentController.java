package com.chris.springcloud.payment.controller;

import com.chris.springcloud.entities.CommonResult;
import com.chris.springcloud.entities.Payment;
import com.chris.springcloud.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/create")
    public CommonResult createPayment(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("insert payment: " + result);
        if (0 < result) {
            return new CommonResult<>(200, "insert payment success." + serverPort, result);
        } else {
            return new CommonResult<>(500, "insert payment fail.");
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("query payment by id:" + id);
        if (null != payment) {
            return new CommonResult<>(200, "query payment success." + serverPort, payment);
        } else {
            return new CommonResult<>(500, "query payment fail.");
        }
    }

    @GetMapping(value = "/get2")
    public CommonResult<Payment> getPaymentById2(Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (null != payment) {
            return new CommonResult<>(200, "query payment success." + serverPort, payment);
        } else {
            return new CommonResult<>(500, "query payment fail.");
        }
    }

    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service:" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return this.discoveryClient;
    }

    @GetMapping("/lb")
    public String getPaymentLB() {
        return serverPort;
    }


    @GetMapping("/feign/timeout")
    public String paymentFeighTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            log.error("error happened", e);
        }
        return serverPort;
    }

}
