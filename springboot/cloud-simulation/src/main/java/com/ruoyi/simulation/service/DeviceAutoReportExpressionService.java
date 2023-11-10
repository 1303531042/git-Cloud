package com.ruoyi.simulation.service;

import com.ruoyi.simulation.domain.DeviceAutoReportExpression;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/8/18
 * @description: 设备上报数据表达式表 业务层
 */
public interface DeviceAutoReportExpressionService {
    /**
     * 通过 设备id查询 属性对应表达式
     * @param deviceId
     * @return
     */
    List<DeviceAutoReportExpression> queryListByDeviceId(Integer deviceId);

    List<DeviceAutoReportExpression> queryList();
}
