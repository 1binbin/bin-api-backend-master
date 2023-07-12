/*
 * com.bin.binclientsdk.client.BinApiClient, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.bin.binclientsdk.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.bin.binclientsdk.constant.HostConstant;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.bin.binclientsdk.utils.SignUtil.genSign;

/**
 * SDK主类
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-16:25:59
 */
public class BinApiClient {
    /**
     * 用户ak
     */
    private String accessKey;
    /**
     * 用户sk
     */
    private String secretKey;

    public BinApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    /**
     * 生成请求头
     * @param body 请求体
     * @param method 请求方法
     * @return 请求头
     * @throws UnsupportedEncodingException 异常
     */
    private Map<String,String> getHeaderMap(String body,String method) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey",accessKey);
        // 随机数，伪装请求头
        map.put("nonce", RandomUtil.randomNumbers(10));
        // 时间戳
        map.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        map.put("sign",genSign(accessKey,secretKey));
        body = URLUtil.encode(body, CharsetUtil.CHARSET_UTF_8);
        map.put("body", body);
        map.put("method", method);
        return map;
    }

    /**
     * 调用接口
     * @param params 请求参数
     * @param url 请求路径
     * @param method 请求方法
     * @return 响应体
     * @throws UnsupportedEncodingException 异常
     */
    public String invokeInterface(String params, String url, String method) throws UnsupportedEncodingException {
        // TODO: 2023/7/12 0012 匹配GET请求
        HttpResponse httpResponse = HttpRequest.post(HostConstant.GATEWAY_HOST + url)
                .header("Accept-Charset", CharsetUtil.UTF_8)
                .addHeaders(getHeaderMap(params, method))
                .body(params)
                .execute();
        return JSONUtil.formatJsonStr(httpResponse.body());
    }
}
