/*
 * com.binbin.binapicommon.service.InnerInterfaceInfoService, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.service;


import com.binbin.binapicommon.mode.entity.InterfaceInfo;

/**
 * 接口信息服务
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:16:40
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     *
     * @param path 请求路径
     * @param method 请求方法
     * @return InterfaceInfo 接口信息
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

}
