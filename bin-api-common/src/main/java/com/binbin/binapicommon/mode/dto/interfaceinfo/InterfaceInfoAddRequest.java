/*
 * com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoAddRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.dto.interfaceinfo;

import com.binbin.binapicommon.mode.vo.RequestParamsRemarkVO;
import com.binbin.binapicommon.mode.vo.ResponseParamsRemarkVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 接口信息创建请求
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:06:47
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 主机名
     */
    private String host;
    /**
     * 接口地址
     */
    private String url;
    /**
     * 请求参数说明
     */
    private String requestParams;
    /**
     * 请求参数说明
     */
    private List<RequestParamsRemarkVO> requestParamsRemark;
    /**
     * 响应参数说明
     */
    private List<ResponseParamsRemarkVO> responseParamsRemark;
    /**
     * 请求头
     */
    private String requestHeader;
    /**
     * 响应头
     */
    private String responseHeader;
    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;
    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口价格
     */
    private Double price;
}
