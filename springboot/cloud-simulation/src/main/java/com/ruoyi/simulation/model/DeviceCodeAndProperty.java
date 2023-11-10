package com.ruoyi.simulation.model;

import com.ruoyi.simulation.domain.SimulationDeviceWrapper;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/8/21
 * @description:
 */
@Data
public class DeviceCodeAndProperty {
    private SimulationDeviceWrapper deviceWrapper;
    private String property;
}
