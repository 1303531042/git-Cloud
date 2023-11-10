package com.ruoyi.common.influxdb.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: KUN
 * @date: 2023/2/22
 * @description: influxDB 客户端配置类
 */
@Data
@Configuration
public class InfluxDBClientConfiguration {
    @Autowired
    private InfluxDBConfig influxDBConfig;

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(influxDBConfig.getUrl(), influxDBConfig.getToken().toCharArray()
                , influxDBConfig.getOrg(), influxDBConfig.getBucket());
    }
}
