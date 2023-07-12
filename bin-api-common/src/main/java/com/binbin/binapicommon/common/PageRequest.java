/*
 * com.binbin.binapicommon.common.PageRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.common;


import com.binbin.binapicommon.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:13:27
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
