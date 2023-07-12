/*
 * com.binbin.binapicommon.service.InnerUserService, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.service;


import com.binbin.binapicommon.mode.entity.User;

/**
 * 用户服务
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:16:17
 */
public interface InnerUserService {

    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     *
     * @param accessKey accessKey
     * @return User 用户信息
     */
    User getInvokeUser(String accessKey);
}
