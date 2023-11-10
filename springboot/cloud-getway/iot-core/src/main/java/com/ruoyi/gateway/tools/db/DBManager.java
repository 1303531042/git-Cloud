package com.ruoyi.gateway.tools.db;

import com.ruoyi.gateway.handle.proxy.ProtocolHandleProxy;
import com.ruoyi.gateway.tools.annotation.IotTable;

import java.util.List;
import java.util.Map;

/**
 * 数据管理
 * @param <P>
 */
public interface DBManager<P extends ProtocolHandleProxy> {

    /**
     * @param tableName
     * @return 返回数据存储元信息
     */
    DBMeta getDBMeta(String tableName);

    /**
     * 移除某个表的元数据对象
     * @param tableName
     * @return
     */
    DBMeta remove(String tableName);

    /**
     * 注册数据存储元对象 如果tableName已经存在则覆盖
     * @param meta
     * @return
     */
    DBMeta register(DBMeta meta);

    /**
     * 注册数据存储元对象 如果tableName已经存在不覆盖
     * @param meta
     * @return
     */
    DBMeta registerIfAbsent(DBMeta meta);

    /**
     * 单条数据入库
     * @param entity {@link Object} or {@link IotMapEntity}
     * @param tableName 用于获取 {@link DBMeta}
     * @see #getDBMeta(String)
     * @return
     */
    int insert(String tableName, Object entity);

    /**
     * 单条数据入库
     * @param value
     * @param tableName 用于获取 {@link DBMeta}
     * @see #getDBMeta(String)
     * @return
     */
    int insert(String tableName, Map<String, Object> value);

    /**
     * @see IotTable
     * @param entityClazz 必须使用注解{@link IotTable}
     * @param entity {@link Object} or {@link IotMapEntity}
     * @return
     */
    int insert(Class entityClazz, Object entity);

    /**
     * 批量数据入库
     * @param tableName 用于获取 {@link DBMeta}
     * @param entities {@link Object} or {@link IotMapEntity}
     * @see #getDBMeta(String)
     * @return
     */
    int batchInsert(String tableName, List<Object> entities);

    /**
     * 批量数据入库
     * @see IotTable
     * @param entityClazz 必须使用注解{@link IotTable}
     * @param entities 支持Map参数
     * @return
     */
    int batchInsert(Class entityClazz, List<Object> entities);

    /**
     * 执行
     * @param entity {@link Object}
     * @param handle
     */
    void execute(Object entity, P handle);
}
