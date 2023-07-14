/*
 * com.binbin.binapicommon.mode.dto.user.UserRegisterRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.dto.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 19:27:27
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String isAdmin;
}
