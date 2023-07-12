/*
 * com.binbin.binapicommon.service.InnerUserInterfaceInfoService, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.service;


import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;

/**
 * 用户接口信息服务类
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:15:51
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId 接口ID
     * @param userId          用户ID
     * @return boolean 是否执行成功
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 是否还有调用次数
     *
     * @param interfaceId 接口id
     * @param userId      用户id
     * @return UserInterfaceInfo 用户接口信息
     */
    UserInterfaceInfo hasLeftNum(Long interfaceId, Long userId);


    /**
     * 添加默认的用户接口信息
     *
     * @param interfaceId 接口id
     * @param userId      用户id
     * @return Boolean 是否添加成功
     */
    Boolean addDefaultUserInterfaceInfo(Long interfaceId, Long userId);

    /**
     * 检查用户是否有接口
     *
     * @param interfaceId 接口id
     * @param userId     用户id
     * @return UserInterfaceInfo 用户接口信息
     */
    UserInterfaceInfo checkUserHasInterface(long interfaceId, long userId);

}
