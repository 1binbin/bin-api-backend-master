/*
 * com.binbin.binapicommon.mode.dto.payInterfaceInfo.PayRequest, 2023-07-22
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.dto.payInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author hongxiaobin
 * @Time 2023/7/22 0022-1:23:42
 */
@Data
public class PayRequest implements Serializable {
    private Integer interfaceInfoId;
    private Integer payNum;
    private static final long serialVersionUID = -5842362260147083227L;
}
