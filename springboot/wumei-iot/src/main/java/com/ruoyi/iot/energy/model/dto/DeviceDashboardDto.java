package com.ruoyi.iot.energy.model.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/11
 * @description: 设备看板dto
 */
@Data
public class DeviceDashboardDto {
    /**
     * 区域id
     */
    private Integer areaId;
    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 设备状态（1-未激活，2-禁用，3-在线，4-离线）
     */
    private Integer status;

    /**
     *  设备编号
     */
    private String serialNumber;

}
