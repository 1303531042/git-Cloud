package com.ruoyi.simulation.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.simulation.domain.vo.SimulationDeviceVo;
import com.ruoyi.simulation.service.SimulationDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description: 模拟设备 控制层
 */
@RestController
@RequestMapping("/SimulationDevice")
public class SimulationDeviceController extends BaseController {
    @Autowired
    private SimulationDeviceService simulationDeviceService;

    @PostMapping
    public AjaxResult addSimulationDevice(@RequestBody SimulationDeviceVo vo) {
        return AjaxResult.success(simulationDeviceService.createSimulationDevice(vo));
    }
}
