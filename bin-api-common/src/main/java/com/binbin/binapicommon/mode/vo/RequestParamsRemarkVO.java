/*
 * com.binbin.binapicommon.mode.vo.RequestParamsRemarkVO, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 请求视图
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:09:26
 */
@Data
public class RequestParamsRemarkVO implements Serializable {

    private static final long serialVersionUID = -6549477882078242340L;
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 是否必须
     */
    private String isRequired;

    /**
     * 类型
     */
    private String type;

    /**
     * 说明
     */
    private String remark;
}
