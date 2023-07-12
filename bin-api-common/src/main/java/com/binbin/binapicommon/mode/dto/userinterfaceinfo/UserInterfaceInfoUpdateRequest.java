/*
 * com.binbin.binapicommon.mode.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 19:28:26
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    private Integer status;


}
