package com.ruoyi.iot.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * id和name
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class DeviceStatistic
{
    /** 设备数量 **/
    private int deviceCount;

    /** 产品数量 **/
    private int productCount;

    /** 告警 **/
    private long alertCount;

    /** 属性上报 **/
    private long propertyCount;

    /** 功能上报 **/
    private long functionCount;

    /** 事件上报 **/
    private long eventCount;

    /** 监测数据上报 **/
    private long monitorCount;


}
