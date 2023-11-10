package com.ruoyi.gateway.tools.db;

import com.ruoyi.gateway.tools.db.rdbms.RdbmsHandle;
import com.ruoyi.gateway.tools.db.rdbms.RdbmsHandleProxyMatcher;
import com.ruoyi.gateway.tools.db.rdbms.RdbmsSqlManager;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class RdbmsAutoConfiguration {

    /**
     * 使用默认数据源
     * @param rdbmsDataSource
     * @return
     */
    @Bean
    public JdbcTemplate rdbmsJdbcTemplate(DataSource rdbmsDataSource) {
        return RdbmsHandle.databaseSource.rdbmsJdbcTemplate = new JdbcTemplate(rdbmsDataSource);
    }

    @Bean
    @ConditionalOnMissingBean(name = {"rdbmsDataSource"})
    public DataSource rdbmsDataSource(IotJdbcProperties properties) {
        if(properties.getDatasource() == null) {
            throw new BeanCreationException("未配置数据源[iot.jdbc.datasource.xx]");
        }

        return properties.getDatasource().build();
    }

    /**
     * 关系型数据库数据采集管理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RdbmsSqlManager.class)
    public RdbmsSqlManager rdbmsManager(JdbcTemplate rdbmsJdbcTemplate) {
        return new RdbmsSqlManager(rdbmsJdbcTemplate);
    }

    /**
     * 关系型数据库采集代理
     * @param rdbmsSqlManager
     * @return
     */
    @Bean
    public RdbmsHandleProxyMatcher rdbmsProtocolHandleProxyMatcher(RdbmsSqlManager rdbmsSqlManager) {
        return new RdbmsHandleProxyMatcher(rdbmsSqlManager);
    }
}
