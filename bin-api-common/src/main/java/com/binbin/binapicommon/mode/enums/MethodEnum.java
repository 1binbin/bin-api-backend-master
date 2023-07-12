/*
 * com.binbin.binapicommon.mode.enums.MethodEnum, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求类型枚举
 *
 * @Author: hongxiaobin
 * @Time: 2023/7/12 0012 16:12:06
 */
@Getter
@AllArgsConstructor
public enum MethodEnum {

    /**
     *
     */
    GET(0,"GET"),
    POST(1,"POST")
    ;
    int code;
    String desc;

    public static String getDesById(Integer code) {
        MethodEnum[] enums = values();
        for (MethodEnum aEnum : enums) {
            if (aEnum.code == code) {
                return aEnum.desc;
            }
        }
        return "";
    }

    public static MethodEnum getById(Integer code) {
        MethodEnum[] enums = values();
        for (MethodEnum aEnum : enums) {
            if (aEnum.code == code) {
                return aEnum;
            }
        }
        return null;
    }


}
