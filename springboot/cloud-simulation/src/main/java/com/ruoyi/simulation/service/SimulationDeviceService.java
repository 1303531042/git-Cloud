package com.ruoyi.simulation.service;

import com.ruoyi.simulation.domain.SimulationDevice;
import com.ruoyi.simulation.domain.vo.SimulationDeviceVo;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description: 模拟设备服务层
 */
public interface SimulationDeviceService {
    SimulationDevice createSimulationDevice(SimulationDeviceVo simulationDeviceVo);

    List<SimulationDevice> queryAll();
}
