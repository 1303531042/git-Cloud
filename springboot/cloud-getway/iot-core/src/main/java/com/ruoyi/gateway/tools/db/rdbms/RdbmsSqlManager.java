package com.ruoyi.gateway.tools.db.rdbms;

import com.ruoyi.gateway.tools.db.DBManager;
import com.ruoyi.gateway.tools.db.DBMeta;
import com.ruoyi.gateway.tools.db.RdbmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RdbmsSqlManager implements DBManager<RdbmsHandle> {

    private JdbcTemplate rdbmsTemplate;
    private Map<String, DBMeta> tableNameAndMetaMap = new HashMap<>();
    private Map<Class<?>, DBMeta> entityClassAndMetaMap = new HashMap<>();
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public RdbmsSqlManager(JdbcTemplate rdbmsTemplate) {
        this.rdbmsTemplate = rdbmsTemplate;
    }

    @Override
    public RdbmsMeta getDBMeta(String tableName) {
        return (RdbmsMeta) tableNameAndMetaMap.get(tableName);
    }

    public RdbmsMeta getDBMeta(Class entityClass) {
        DBMeta dbMeta = entityClassAndMetaMap.get(entityClass);
        if(dbMeta == null) {
            synchronized (this) {
                dbMeta = entityClassAndMetaMap.get(entityClass);
                if(dbMeta == null) {
                    dbMeta = new RdbmsMeta(entityClass);
                    entityClassAndMetaMap.put(entityClass, dbMeta);
                }
            }
        }

        return (RdbmsMeta) dbMeta;
    }

    @Override
    public RdbmsMeta remove(String tableName) {
        return (RdbmsMeta) tableNameAndMetaMap.get(tableName);
    }

    @Override
    public RdbmsMeta register(DBMeta meta) {
        if(meta instanceof RdbmsMeta) {
            return (RdbmsMeta) tableNameAndMetaMap.put(meta.getTableName(), meta);
        }

        throw new RdbmsException("只支持["+RdbmsMeta.class.getSimpleName()+"]类型对象");
    }

    @Override
    public RdbmsMeta registerIfAbsent(DBMeta meta) {
        if(!tableNameAndMetaMap.containsKey(meta.getTableName())) {
            return this.register(meta);
        }

        return null;
    }

    @Override
    public int insert(String tableName, Object entity) {
        RdbmsMeta dbMeta = this.getDBMeta(tableName);
        List<SqlParameterValue> values = dbMeta.getParams(entity);

        return this.execUpdate(rdbmsTemplate, dbMeta,1, values);
    }

    @Override
    public int insert(String tableName, Map<String, Object> value) {
        RdbmsMeta dbMeta = this.getDBMeta(tableName);
        List<SqlParameterValue> values = dbMeta.getParams(value);

        return this.execUpdate(rdbmsTemplate, dbMeta, 1, values);
    }

    @Override
    public int insert(Class entityClazz, Object entity) {
        RdbmsMeta dbMeta = this.getDBMeta(entityClazz);
        List<SqlParameterValue> values = dbMeta.getParams(entity);

        return this.execUpdate(rdbmsTemplate, dbMeta, 1, values);
    }

    @Override
    public int batchInsert(String tableName, List<Object> entities) {
        RdbmsMeta dbMeta = this.getDBMeta(tableName);
        List<SqlParameterValue> values = new ArrayList<>();
        for (Object entity : entities) {
            values.addAll(dbMeta.getParams(entity));
        }

        return execUpdate(rdbmsTemplate, dbMeta, entities.size(), values);
    }

    @Override
    public int batchInsert(Class entityClazz, List<Object> entities) {
        RdbmsMeta dbMeta = this.getDBMeta(entityClazz);
        List<SqlParameterValue> values = new ArrayList<>();
        for (Object entity : entities) {
            values.addAll(dbMeta.getParams(entity));
        }

        return execUpdate(rdbmsTemplate, dbMeta, entities.size(), values);
    }

    @Override
    public void execute(Object entity, RdbmsHandle handle) {
        RdbmsMeta dbMeta = this.getDBMeta(entity.getClass());
        List<SqlParameterValue> values = dbMeta.getParams(entity);
        this.execUpdate(handle.rdbmsJdbcTemplate(entity), dbMeta, 1, values);
    }

    protected int execUpdate(JdbcTemplate jdbcTemplate, RdbmsMeta meta, int size, List<SqlParameterValue> values) {
        long start = System.currentTimeMillis();
        String sqlStatement = size == 1 ? meta.getStatement() : meta.getStatement(size);
        try {
            return jdbcTemplate.update(sqlStatement, values.toArray());
        } finally {
            if(logger.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder();
                for (SqlParameterValue value : values) {
                    sb.append(value.getValue()).append(", ");
                }

                logger.trace("Rdbms适配 数据入库({}ms) - 条数：{}\r\n\t    Sql：{} \r\n\t params：{}"
                        , System.currentTimeMillis() - start, size, sqlStatement, sb.substring(0, sb.length() - 2));
            }
        }

    }
}
