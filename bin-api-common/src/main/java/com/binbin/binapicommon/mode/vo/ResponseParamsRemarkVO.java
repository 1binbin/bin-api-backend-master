/*
 * com.binbin.binapicommon.mode.vo.ResponseParamsRemarkVO, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:09:36
 */
@Data
public class ResponseParamsRemarkVO implements Serializable {
    private static final long serialVersionUID = 3673526667928077840L;
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 说明
     */
    private String remark;
}
