package com.ruoyi.gateway.tools.db;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IotJdbc {

    /**
     * jdbc数据库操作模板
     * @param entity 操作的实体 方便根据此实体获取不同的数据源
     * @return
     */
    JdbcTemplate jdbcTemplate(Object entity);
}
