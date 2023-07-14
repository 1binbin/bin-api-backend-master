/*
 * com.binbin.binapicommon.constant.UserConstant, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.constant;

/**
 * 用户常量
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:12:16
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    /**
     * 用户信息盐值
     */
    String USER_SALT = "bin";

    /**
     * 注册用户账号最短长度
     */
    Integer USER_ACCOUNT_LENGTH = 4;

    /**
     * 注册用户密码最短长度
     */
    Integer USER_PASSWORD_LENGHT =8;

    /**
     * 用户默认头像
     */
    String USER_AVTURAL = "http://rxqlbkl6h.hn-bkt.clouddn.com/openapi/common/commonAva.png";
}
