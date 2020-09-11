package com.chris.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

    /**
     * 当访问地址http://localhost:9527/guonei时会自动转发到https//news.baidu.com/guonei
     */
    @Bean
    public RouteLocator customRouteLocator_guoji(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("guoji_news", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }

    /**
     * 当访问地址http://localhost:9527/mil 时会自动转发到https//news.baidu.com/mil
     */
    @Bean
    public RouteLocator customRouteLocator_mil(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("guoji_news", r -> r.path("/mil").uri("http://news.baidu.com/mil")).build();
        return routes.build();
    }


}


