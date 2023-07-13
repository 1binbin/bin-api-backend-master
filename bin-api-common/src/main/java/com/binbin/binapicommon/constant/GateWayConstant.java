/*
 * com.binbin.binapicommon.constant.GateWayConstant, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.constant;

import java.util.Collections;
import java.util.List;

/**
 * 网关常量
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-9:41:57
 */
public interface GateWayConstant {
    /**
     * IP地址列表
     */
    List<String> IP_WHITE_LIST = Collections.singletonList("127.0.0.1");
}
