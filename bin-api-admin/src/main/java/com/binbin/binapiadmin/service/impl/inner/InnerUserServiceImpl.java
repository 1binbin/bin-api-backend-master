/*
 * com.binbin.binapiadmin.service.impl.inner.InnerUserServiceImpl, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.service.impl.inner;

import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-17:39:38
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     *
     * @param accessKey accessKey
     * @return User 用户信息
     */
    @Override
    public User getInvokeUser(String accessKey) {
        return null;
    }
}
