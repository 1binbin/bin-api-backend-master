/*
 * com.binbin.binapigateway.exception.GlobalFilterExceptionHandler, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapigateway.exception;

import com.binbin.binapicommon.common.BaseResponse;
import com.binbin.binapicommon.common.ResultUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理器
 *
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-10:05:12
 */
@Slf4j
@Component
public class GlobalFilterExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // 设置返回json
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }
        return handlerBusinessException(exchange, ex);
    }

    private Mono<Void> handlerBusinessException(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            try {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                log.error("{}\n {}", ex.getMessage(), ex.getStackTrace());
                BaseResponse<Object> fail = ResultUtils.fail(ex.getMessage());
                log.error("{}", fail);
                return dataBufferFactory.wrap(objectMapper.writeValueAsBytes(fail));
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("异常：{}", jsonProcessingException.getMessage());
                return dataBufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
