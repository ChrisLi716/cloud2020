package com.chris.springcloud.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("************** come in MyLogGateWayFilter :" + new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (StrUtil.isEmpty(uname)) {
            log.info("************** illegal uname :" + new Date());
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 加载过虑器的顺序
     * 一般数字或小，优先级越高
     * 全局都是0，放在第一位加载
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
