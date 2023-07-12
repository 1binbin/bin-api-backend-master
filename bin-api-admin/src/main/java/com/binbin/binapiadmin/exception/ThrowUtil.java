/*
 * com.binbin.binapiadmin.exception.ThrowUtil, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.exception;

import com.binbin.binapicommon.common.ErrorCode;

/**
 * 异常抛出工具类
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-18:03:36
 */
public class ThrowUtil {
    /**
     * 条件成立这则抛出异常
     * @param condition 条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition,RuntimeException runtimeException){
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立这则抛出异常
     * @param condition 条件
     * @param errorCode 错误码
     */
    public static void throwIf(boolean condition, ErrorCode errorCode){
        throwIf(condition,new BusinessException(errorCode));
    }

    /**
     * 条件成立这则抛出异常
     * @param condition 条件
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static void throwIf(boolean condition,ErrorCode errorCode, String message){
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
