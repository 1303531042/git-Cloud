package com.ruoyi.simulation.domain.vo;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description:
 */
@Data
public class SimulationDeviceVo {
    private Integer productId;
    private String simulationName;
    private String channelCode;
    private String deviceCode;
    private String deviceName;
    private String thingsModel;
}
