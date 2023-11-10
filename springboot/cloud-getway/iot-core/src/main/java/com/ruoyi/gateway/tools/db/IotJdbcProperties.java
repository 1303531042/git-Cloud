package com.ruoyi.gateway.tools.db;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;

@ConfigurationProperties(prefix = "iot.jdbc")
public class IotJdbcProperties {

    private JdbcDataSourceProperties datasource;

    public JdbcDataSourceProperties getDatasource() {
        return datasource;
    }

    public void setDatasource(JdbcDataSourceProperties datasource) {
        this.datasource = datasource;
    }

    public static class JdbcDataSourceProperties {

        public DataSource build() {
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            if(getType() == null) {
                throw new IllegalArgumentException("未指定数据源类型[iot.jdbc.datasource.type]");
            }

            dataSourceProperties.setUrl(getUrl());
            dataSourceProperties.setType(getType());
            dataSourceProperties.setPassword(getPassword());
            dataSourceProperties.setUsername(getUsername());
            dataSourceProperties.setDriverClassName(getDriverClassName());
            return dataSourceProperties.initializeDataSourceBuilder().build();
        }

        /**
         * 数据源类型
         */
        private Class<? extends DataSource> type;

        /**
         * 数据源驱动类
         */
        private String driverClassName;

        /**
         * JDBC URL of the database.
         */
        private String url;

        /**
         * Login username of the database.
         */
        private String username;

        /**
         * Login password of the database.
         */
        private String password;

        public Class<? extends DataSource> getType() {
            return type;
        }

        public void setType(Class<? extends DataSource> type) {
            this.type = type;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
