package com.ruoyi.gateway.tools.annotation;

/**
 * 使用于在插入数据时自动创建数据表的情况
 */
public interface TagsResolver {

    /**
     * 解析出tags的值, 此值最好使用缓存提升性能
     * @return 如果返回null则此tag将不加入插入语句，taos数据库将默认设置此tag值为null
     */
    Object resolve(String tableName, String tagName);
}
