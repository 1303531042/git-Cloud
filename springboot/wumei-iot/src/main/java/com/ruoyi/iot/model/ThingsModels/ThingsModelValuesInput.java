package com.ruoyi.iot.model.ThingsModels;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 设备输入物模型值参数
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ThingsModelValuesInput
{
    /** 产品ID **/
    private Long productId;

    private Long deviceId;

    /** 设备ID **/
    private String deviceNumber;

    /** 设备物模型值的字符串格式 **/
    private String stringValue;

    private Date createDate;

    /** 设备物模型值的集合 **/
    private List<ThingsModelValueRemarkItem> thingsModelValueRemarkItem;

}
