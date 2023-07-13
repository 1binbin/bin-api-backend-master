/*
 * com.binbin.binapiinterface.controller.NetEaseController, 2023-07-13
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
 * 模拟接口控制器3
 *
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-9:29:26
 */
@RestController
public class NetEaseController {
    /**
     * 获取网易云音乐评论
     *
     * @param request 域对象
     * @return 响应体
     */
    @PostMapping("/api/comments.163")
    public String hotComments(HttpServletRequest request) {
        String url = "https://api.uomg.com/api/comments.163";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        System.out.println(body);
        HttpResponse httpResponse = HttpRequest.post(url)
                .body(body)
                .execute();
        return httpResponse.body();
    }

    /**
     * 获取随机音乐
     *
     * @param request 域对象
     * @return 响应体
     */
    @PostMapping("/api/rand.music")
    public String randMusic(HttpServletRequest request) {
        String url = "https://api.uomg.com/api/rand.music";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();
        return httpResponse.body();
    }
}
