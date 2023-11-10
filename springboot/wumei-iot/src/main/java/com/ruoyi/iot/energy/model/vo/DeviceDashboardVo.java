package com.ruoyi.iot.energy.model.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/10/11
 * @description:
 */
@Data
public class DeviceDashboardVo {
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 设备编号
     * */
    private String serialNumber;

    /**
     * 设备状态（1-未激活，2-禁用，3-在线，4-离线）
     */
    private Integer status;

    /**
     * 属性信息
     */
    private List<String> propertyValues;
}
