/*
 * com.binbin.binapigateway.filter.CustomGlobalFilter, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapigateway.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.bin.binclientsdk.utils.SignUtil;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.constant.GateWayConstant;
import com.binbin.binapicommon.constant.RedisConstant;
import com.binbin.binapicommon.mode.entity.InterfaceInfo;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import com.binbin.binapicommon.service.InnerInterfaceInfoService;
import com.binbin.binapicommon.service.InnerUserInterfaceInfoService;
import com.binbin.binapicommon.service.InnerUserService;
import com.binbin.binapigateway.exception.BusinessException;
import jodd.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
        // 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String hostString = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        HttpHeaders headers = request.getHeaders();
        String path = headers.getFirst("url");
        log.info("请求唯一标识：{}", request.getId());
        log.info("请求路径：{}", path);
        log.info("请求参数：{}", request.getQueryParams());
        log.info("请求来源地址：{}", hostString);
        log.info("请求来源地址：{}", request.getRemoteAddress());

        ServerHttpResponse response = exchange.getResponse();
        // 黑白名单
        if (!GateWayConstant.IP_WHITE_LIST.contains(hostString)) {
            return handleNoAuth(response);
        }
        // 用户鉴权（判断ak，sk）
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String nonce = headers.getFirst("nonce");
        String sign = headers.getFirst("sign");
        String body = URLUtil.decode(headers.getFirst("body"), CharsetUtil.CHARSET_UTF_8);
        String method = headers.getFirst("method");
        if (StringUtil.isEmpty(nonce)
                || StringUtil.isEmpty(sign)
                || StringUtil.isEmpty(timestamp)
                || StringUtil.isEmpty(method)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求头参数不完整！");
        }

        User invokeUser = innerUserService.getInvokeUser(accessKey);
        if (invokeUser == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "用户accessKey不合法");
        }
        // 判断随机数时候存在，防止重放攻击
        String existNonce = (String) redisTemplate.opsForValue().get(nonce);
        if (StringUtil.isNotBlank(existNonce)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求重复！");
        }
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long difference = currentTimeMillis - Long.parseLong(timestamp);
        if (Math.abs(difference) > GateWayConstant.TIMESTAMP_OUT_TIME) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求超时！");
        }
        // 校验签名
        // 应该通过 accessKey 查询数据库中的 secretKey 生成 sign 和前端传递的 sign 对比
        String serverSign = SignUtil.genSign(body, invokeUser.getSecretKey());
        if (!sign.equals(serverSign)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "签名错误！");
        }
        // 判断接口是否存在
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInvokeInterface error", e);
        }
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口不存在!");
        }
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理响应
     *
     * @param exchange        对象
     * @param chain           过滤链
     * @param interfaceInfoId 接口id
     * @param userId          用户id
     * @return Mono对象
     */
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            postHandler(exchange.getRequest(), exchange.getResponse(), interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口调用次数 + 1 失败！");
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    })
                            );
                        } else {
                            // 8. 调用失败，返回规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            //降级处理返回数据
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("网关处理异常响应.\n" + e);
            return chain.filter(exchange);
        }
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

    /**
     * 响应没权限
     *
     * @param response 响应
     * @return Mono对象
     */
    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.setRawStatusCode(HttpStatus.FORBIDDEN.value());
        return response.setComplete();
    }

    /**
     * post响应器
     *
     * @param request         请求
     * @param response        响应
     * @param interfaceInfoId 接口id
     * @param userId          用户id
     */
    private void postHandler(ServerHttpRequest request,
                             ServerHttpResponse response,
                             Long interfaceInfoId,
                             Long userId) {
        RLock lock = redissonClient.getLock(RedisConstant.REDISSON_LOCK_KEY);
        if (response.getStatusCode() == HttpStatus.OK) {
            CompletableFuture.runAsync(() -> {
                if (lock.tryLock()) {
                    try {
                        addInterfaceNum(request, interfaceInfoId, userId);
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }
    }

    /**
     * 添加用户接口数量
     *
     * @param request         请求
     * @param interfaceInfoId 接口id
     * @param userId          用户id
     */
    private void addInterfaceNum(ServerHttpRequest request, Long interfaceInfoId, Long userId) {
        // 防止刷接口
        String nonce = request.getHeaders().getFirst("nonce");
        if (StringUtil.isEmpty(nonce)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求重复");
        }
        UserInterfaceInfo userInterfaceInfo = innerUserInterfaceInfoService.hasLeftNum(interfaceInfoId, userId);
        if (userInterfaceInfo == null) {
            Boolean save = innerUserInterfaceInfoService.addDefaultUserInterfaceInfo(interfaceInfoId, userId);
            if (save == null || !save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口绑定用户失败");
            }
        }
        if (userInterfaceInfo != null && userInterfaceInfo.getLeftNum() <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口可调用次数已用完");
        }
        // 保存nonce，签名认证，防止刷接口
        redisTemplate.opsForValue().set(nonce, 1, 5, TimeUnit.MINUTES);
        innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
