package com.ruoyi.common.influxdb.wrapper;


import com.ruoyi.common.influxdb.Measudirection;

/**
 * @auther: KUN
 * @date: 2023/7/10
 * @description: 包装器接口
 */
public interface FluxWrapper<W extends Measudirection> {
    /**
     * 获取该column的类型
     *
     * @param columnName
     * @return
     */
    Class<?> getColumnClass(String columnName);


}
