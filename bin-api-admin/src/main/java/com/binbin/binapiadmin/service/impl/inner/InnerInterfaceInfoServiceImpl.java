/*
 * com.binbin.binapiadmin.service.impl.inner.InnerInterfaceInfoServiceImpl, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.service.impl.inner;

import com.binbin.binapiadmin.service.InterfaceInfoService;
import com.binbin.binapicommon.mode.entity.InterfaceInfo;
import com.binbin.binapicommon.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-17:38:42
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     *
     * @param path   请求路径
     * @param method 请求方法
     * @return InterfaceInfo 接口信息
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        return interfaceInfoService.query()
                .eq("url", path)
                .eq("method", method)
                .one();
    }
}
