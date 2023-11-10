package com.ruoyi.common.influxdb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: KUN
 * @date: 2023/2/22
 * @description: influxdb配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "influxdb")
public class InfluxDBConfig {
    private String url;
    private String token;
    private String org;
    private String bucket;
}
