/*
 * com.bin.binclientsdk.BinApiClientConfig, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.bin.binclientsdk;

import com.bin.binclientsdk.client.BinApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SDK配置类
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-16:23:03
 */
@Data
@Configuration
/*
  配置前缀
 */
@ConfigurationProperties("bin.client")
@ComponentScan
public class BinApiClientConfig {
    /**
     * 用户ak
     */
    private String accessKey;
    /**
     * 用户sk
     */
    private String secretKey;

    @Bean
    public BinApiClient getBinApiClient() {
        return new BinApiClient(accessKey, secretKey);
    }
}
