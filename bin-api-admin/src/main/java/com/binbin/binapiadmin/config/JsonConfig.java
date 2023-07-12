/*
 * com.binbin.binapiadmin.config.JsonConfig, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * SpringMVC JSON配置
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-17:50:43
 */
@JsonComponent
public class JsonConfig {
    /**
     * 添加Long转json精度丢失问题
     *
     * @param builder json
     * @return 处理完成的对象
     */
    @Bean
    public ObjectMapper jasksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
