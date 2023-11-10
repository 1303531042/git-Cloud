package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/10/11
 * @description:
 */
@Data
public class DeviceDashboardBo {
    /**
     * 子区域列表
     */
    private List<Integer> areaIds;
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
