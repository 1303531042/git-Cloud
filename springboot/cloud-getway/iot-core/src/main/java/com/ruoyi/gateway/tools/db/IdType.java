package com.ruoyi.gateway.tools.db;

public enum IdType {

    /**
     * 不配置
     */
    Void,
    /**
     * 关系型数据库id字段值的生成方式
     * 雪花算法 long类型
     */
    SnowFlake,
}
