package com.ruoyi.simulation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/8/18
 * @description: 设备上报数据表达式表
 */
@Data
@TableName("iot_device_auto_report_expression")
public class DeviceAutoReportExpression {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 设备属性唯一标识
     */
    private String identity;
    /**
     * 属性对应表达式
     */
    private String expression;

}
