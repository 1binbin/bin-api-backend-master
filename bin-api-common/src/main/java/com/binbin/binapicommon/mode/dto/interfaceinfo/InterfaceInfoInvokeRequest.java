/*
 * com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoInvokeRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口信息测试调用请求类
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:07:13
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 主机号
     */
    private String host;
}
