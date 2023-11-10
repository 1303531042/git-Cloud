package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/12
 * @description:
 */
@Data
public class AreaDeviceStatusBo {
    /**
     * 在线总数
     */
    private Integer onlineTotal;
    /**
     * 离线总数
     */
    private Integer OfflineTotal;
}
