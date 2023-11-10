package com.ruoyi.iot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 设备分组对象 iot_device_group
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@TableName("iot_device_group")
public class DeviceGroup
{
    private static final long serialVersionUID = 1L;

    /** 分组ID */
    private Long groupId;

    /** 设备ID */
    private Long deviceId;




}
