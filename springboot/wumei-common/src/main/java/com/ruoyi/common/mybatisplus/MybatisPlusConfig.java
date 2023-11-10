package com.ruoyi.common.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: KUN
 * @date: 2023/7/27
 * @description: 多租户配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tenant")
public class MybatisPlusConfig {
    /**
     * 租户id 在数据库中的列名
     */
    private String tenantIdColumn;

    /**
     * 需要忽略的表列表 用；分开 ，可用contains方法
     */
    private String ignoreTables;
}
