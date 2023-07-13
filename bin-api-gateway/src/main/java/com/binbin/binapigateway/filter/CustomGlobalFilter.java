/*
 * com.binbin.binapigateway.filter.CustomGlobalFilter, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapigateway.filter;

import com.binbin.binapicommon.service.InnerInterfaceInfoService;
import com.binbin.binapicommon.service.InnerUserInterfaceInfoService;
import com.binbin.binapicommon.service.InnerUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 统一配置文件
 *
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-9:43:21
 */
@Component
@Slf4j
@Data
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerUserService innerUserService;
    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 过滤器
     *
     * @param exchange 对象
     * @param chain    过滤链
     * @return Mono对象
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    /**
     * 优先级
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
