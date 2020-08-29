package com.chris.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentService {

    /**
     * @param id
     * @return 正常访问
     */
    public String paymentInfo_OK(int id) {
        return "thread pool " + Thread.currentThread().getName() + " paymentInfo_OK, id:" + id + ".";
    }

    /**
     * 带有服务降级的超时访问
     * execution.isolation.thread.timeoutInMilliseconds 在抽象类HystrixCommandProperties中定义
     *
     * @param id
     * @return 访问超时
     */
    @HystrixCommand(fallbackMethod = "timeOutHandler", commandProperties = {@HystrixProperty(name =
            "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfo_TimeOut(int id) {
        int sleepTime = 5000;
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error("error happened paymentInfo_TimeOut ", e);
        }
        return "thread pool " + Thread.currentThread().getName() + " paymentInfo_TimeOut, id:" + id + ". waste " +
                "time" + "seconds:" + sleepTime;
       /*  模拟其它系统异常
        int i = 10 / 0;
        return "thread pool " + Thread.currentThread().getName() + " paymentInfo_TimeOut, id:" + id;*/
    }

    @SuppressWarnings("unused")
    private String timeOutHandler(int id) {
        return "thread pool " + Thread.currentThread().getName() + " timeOutHandler, system busy or " +
                "server callapse, id:" + id;
    }


    /**
     * 模拟服务熔断
     * 配置中的属性在抽象类HystrixCommandProperties中定义
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallBack", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"), //trip circut before retry 的时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")}) //失败率达到多少后启动熔断
    public String paymentCircuitBreaker(int id) {
        if (id < 0) {
            throw new RuntimeException("id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t 调用成功，流水号: " + serialNumber;
    }

    @SuppressWarnings("unused")
    private String paymentCircuitBreakerFallBack(int id) {
        return "id 不能为负数, id:" + id;
    }


}
