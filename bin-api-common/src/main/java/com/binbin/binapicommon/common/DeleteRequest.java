/*
 * com.binbin.binapicommon.common.DeleteRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:14:17
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
