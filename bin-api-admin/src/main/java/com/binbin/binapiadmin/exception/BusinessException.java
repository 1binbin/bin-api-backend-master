/*
 * com.binbin.binapiadmin.exception.BusinessException, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.exception;

import com.binbin.binapicommon.common.ErrorCode;

/**
 * 自定义异常
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-18:01:17
 */
public class BusinessException extends RuntimeException {
    /**
     * 状态码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
