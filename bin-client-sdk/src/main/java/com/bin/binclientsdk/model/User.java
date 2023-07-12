/*
 * com.bin.binclientsdk.model.User, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.bin.binclientsdk.model;

import lombok.Data;
import lombok.ToString;

/**
 * 用户信息
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-16:35:17
 */
@Data
@ToString
public class User {
    /**
     * 用户名
     */
    private String username;

    /**
     * 主机号
     */
    private String host;

}
