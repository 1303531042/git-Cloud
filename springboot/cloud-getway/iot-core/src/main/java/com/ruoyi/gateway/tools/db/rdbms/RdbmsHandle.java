package com.ruoyi.gateway.tools.db.rdbms;

import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.handle.proxy.ProtocolHandleProxy;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 关系型数据库数据采集代理
 * @param <T>
 */
public interface RdbmsHandle<T extends Protocol> extends ProtocolHandleProxy<T> {

    DefaultRdbmsDatabaseSource databaseSource = new DefaultRdbmsDatabaseSource();

    default JdbcTemplate rdbmsJdbcTemplate(Object entity) {
        return databaseSource.rdbmsJdbcTemplate;
    }

    /**
     * 默认的Rdbms数据源
     */
    class DefaultRdbmsDatabaseSource {
        public JdbcTemplate rdbmsJdbcTemplate;
    }
}
