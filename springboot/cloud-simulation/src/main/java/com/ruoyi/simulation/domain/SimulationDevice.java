package com.ruoyi.simulation.domain;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

/**
 * 设备对象 iot_device
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@TableName("simulation_device")
public  class SimulationDevice {
    /** 物模型*/
    private String thingsModel;

    /** 物模型值*/
    private String thingsModelValue;

    /** ID */
    @TableId
    private Integer id;

    /** 产品ID */
    private Integer productId;

    /** 设备名  */
    private String deviceName;

    /** 设备编号 唯一 */
    private String deviceCode;

    /**
     * channel 编号不唯一
     */
    private String channelCode;


    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线，5-数据断线） */
    private Integer status;

    /** 设备入网IP */
    private String networkIp;


    /** 租户ID */
    private Long tenantId;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;





    public SimulationDeviceWrapper tpWrapper() {
        return new SimulationDeviceWrapper(this);
    }

}
