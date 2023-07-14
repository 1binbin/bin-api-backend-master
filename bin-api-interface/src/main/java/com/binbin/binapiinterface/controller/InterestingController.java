/*
 * com.binbin.binapiinterface.controller.InterestingController, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiinterface.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟接口控制器2
 *
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-9:29:02
 */
@RestController
public class InterestingController {

    /**
     * 统一请求接口
     *
     * @param request 请求域对象
     * @return 响应数据
     */
    @PostMapping("/invoke")
    public String invoke(HttpServletRequest request) {
        HttpResponse httpResponse;
        String host = request.getHeader("realhost");
        String url = request.getHeader("url");
        // 实际请求参数
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        // 完整路径
        String fullUrl = host + url;
        String method = request.getHeader("method");
        switch (method) {
            case "GET":
            case "GET/POST":
                httpResponse = HttpRequest.get(fullUrl + "?" + body).execute();
                break;
            case "POST":
            case "POST/GET":
                httpResponse = HttpRequest.post(fullUrl).body(body).execute();
                break;
            default:
                return "请求方法不支持，只支持 GET 或 POST";
        }
        if (httpResponse.body() == null) {
            return "接口请求结果为空";
        }
        return httpResponse.body();
    }
}
