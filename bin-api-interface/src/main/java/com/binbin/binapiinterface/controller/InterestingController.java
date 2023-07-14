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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Value("server.port")
    private String port;

    /**
     * 获取随机头像
     *
     * @param request 域对象
     * @return 响应体
     */
    @PostMapping("/api/rand.avatar")
    public String randAvatar(HttpServletRequest request) {
        String url = "https://api.uomg.com/api/rand.avatar";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();
        System.out.println("获取随机头像");
        return httpResponse.body();
    }

    /**
     * 获取随机照片
     *
     * @param request 域对象
     * @return 响应体
     */
    @PostMapping("/sjbz/api.php")
    public String randImages(HttpServletRequest request) {
        String url = "http://api.btstu.cn/sjbz/api.php";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();
        System.out.println("获取随机照片");
        return httpResponse.body();
    }

    /**
     * 获取随机语录
     *
     * @param request 域对象
     * @return 响应体
     */
/*    @PostMapping("/yan/api.php")
    public String poisonChicken(HttpServletRequest request) {
        String url = "http://api.btstu.cn/yan/api.php";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();
        System.out.println("获取随机语录");
        return httpResponse.body();
    }*/

    /**
     * 长网址还原
     *
     * @param request 域对象
     * @return 响应体
     */
    @PostMapping("/api/long2dwz")
    public String long2dwz(HttpServletRequest request) {
        String url = "https://api.uomg.com/api/long2dwz";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();
        System.out.println("长网址还原");
        return httpResponse.body();
    }

    @GetMapping("/error")
    public String errorResponse() {
        return "请求方法不支持，只支持 GET 或 POST";
    }

    @PostMapping("/invoke")
    public String invoke(HttpServletRequest request) {
        HttpResponse httpResponse;
        String host = request.getHeader("host");
        String url = request.getHeader("url");
        // 实际请求参数
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        // 完整路径
        String fullUrl = host + url;
        String method = request.getHeader("method");
        switch (method) {
            case "GET":
                httpResponse = HttpRequest.get(fullUrl + "?" + body).execute();
                break;
            case "POST":
                httpResponse = HttpRequest.post(fullUrl).body(body).execute();
                break;
            default:
                httpResponse = HttpRequest.get(port + "/error").execute();
        }
        return httpResponse.body();
    }
}
