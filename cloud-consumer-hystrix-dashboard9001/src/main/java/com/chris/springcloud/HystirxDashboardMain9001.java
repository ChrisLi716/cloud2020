package com.chris.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystirxDashboardMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystirxDashboardMain9001.class, args);
    }
}
