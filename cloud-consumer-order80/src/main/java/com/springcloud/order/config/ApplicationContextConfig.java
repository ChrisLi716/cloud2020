package com.springcloud.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    //手写Ribbon负载均衡算法时去掉@LoadBalanced
    //@LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
