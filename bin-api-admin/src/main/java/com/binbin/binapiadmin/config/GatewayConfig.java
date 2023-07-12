/*
 * com.binbin.binapiadmin.config.GatewayComnfig, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-17:49:16
 */
@Configuration
@ConfigurationProperties(prefix = "bin.gateway")
@Data
public class GatewayConfig {
    private String host;
}
