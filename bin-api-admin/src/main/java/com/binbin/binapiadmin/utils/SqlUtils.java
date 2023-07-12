/*
 * com.binbin.binapiadmin.utils.SqlUtils, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 20:14:06
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField 排序字段
     * @return 是否合法
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
