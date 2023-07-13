/*
 * com.binbin.binapiinterface.controller.NameController, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiinterface.controller;

import com.binbin.binapicommon.mode.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟接口控制器1
 *
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-9:25:17
 */
@RestController
public class NameController {

    /**
     * 获取名称
     *
     * @param user    用户信息
     * @param request 域对象
     * @return 响应体名称
     */
    @PostMapping("/api/name/user")
    public String getUserNameByPost(@RequestBody User user,
                                    HttpServletRequest request) {
        return "POST——你的用户名字是：" + user.getUserName();
    }
}
