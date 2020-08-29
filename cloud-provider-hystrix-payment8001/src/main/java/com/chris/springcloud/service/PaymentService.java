package com.chris.springcloud.service;

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
       /* int i = 10 / 0;
        return "thread pool " + Thread.currentThread().getName() + " paymentInfo_TimeOut, id:" + id;*/
    }

    @SuppressWarnings("unused")
    private String timeOutHandler(int id) {
        return "thread pool " + Thread.currentThread().getName() + " timeOutHandler, system busy or " +
                "server callapse, id:" + id;
    }

}
