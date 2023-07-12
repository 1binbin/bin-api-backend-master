/*
 * com.binbin.binapiadmin.service.impl.inner.InnerUserInterfaceInfoServiceImpl, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.service.impl.inner;

import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import com.binbin.binapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-17:39:14
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    /**
     * 调用接口统计
     *
     * @param interfaceInfoId 接口ID
     * @param userId          用户ID
     * @return boolean 是否执行成功
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return false;
    }

    /**
     * 是否还有调用次数
     *
     * @param interfaceId 接口id
     * @param userId      用户id
     * @return UserInterfaceInfo 用户接口信息
     */
    @Override
    public UserInterfaceInfo hasLeftNum(Long interfaceId, Long userId) {
        return null;
    }

    /**
     * 添加默认的用户接口信息
     *
     * @param interfaceId 接口id
     * @param userId      用户id
     * @return Boolean 是否添加成功
     */
    @Override
    public Boolean addDefaultUserInterfaceInfo(Long interfaceId, Long userId) {
        return null;
    }

    /**
     * 检查用户是否有接口
     *
     * @param interfaceId 接口id
     * @param userId      用户id
     * @return UserInterfaceInfo 用户接口信息
     */
    @Override
    public UserInterfaceInfo checkUserHasInterface(long interfaceId, long userId) {
        return null;
    }
}
