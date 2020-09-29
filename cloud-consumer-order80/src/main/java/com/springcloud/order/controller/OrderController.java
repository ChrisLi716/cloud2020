package com.springcloud.order.controller;

import com.chris.springcloud.entities.CommonResult;
import com.chris.springcloud.entities.Payment;
import com.springcloud.order.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

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

    @Resource
    private LoadBalancer myLB;

    @Resource
    private DiscoveryClient discoveryClient;


    @GetMapping("/payment/create")
    @SuppressWarnings("unchecked")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/api/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/payment/create2")
    @SuppressWarnings("unchecked")
    public CommonResult<Payment> create2(Payment payment) {
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/api/payment/create",
                payment, CommonResult.class);
        log.info("status:" + entity.getStatusCode() + ",head:" + entity.getHeaders());
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "create2 method operation fail");
        }
    }


    @GetMapping("/payment/get/{id}")
    @SuppressWarnings("unchecked")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/api/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/payment/get2/{id}")
    @SuppressWarnings("unchecked")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/api/payment/get/" + id,
                CommonResult.class);
        log.info("status:" + entity.getStatusCode() + ",head:" + entity.getHeaders());
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult(444, "get2 mothod opration fail");
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }
        ServiceInstance serviceInstance = myLB.instances(instances);
        URI uri = serviceInstance.getUri();
        log.info("uri:" + uri);
        return restTemplate.getForObject(uri + "/api/payment/lb", String.class);
    }

    @GetMapping("/payment/zipkin")
    public String testPaymentZipkin() {
        return restTemplate.getForObject(PAYMENT_URL + "/api/payment/zipkin", String.class);
    }

}
