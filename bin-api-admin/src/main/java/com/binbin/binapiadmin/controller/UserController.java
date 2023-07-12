/*
 * com.binbin.binapiadmin.controller.UserController, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.controller;

import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapicommon.common.BaseResponse;
import com.binbin.binapicommon.mode.dto.user.UserRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户信息控制器
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-18:08:25
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return null;
    }
}
