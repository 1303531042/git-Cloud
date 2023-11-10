package com.ruoyi.simulation.controller;

import com.ruoyi.simulation.domain.vo.ParamExplainVo;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.simulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description:  模拟层 控制层
 */

@RestController
@RequestMapping("/simulation")
public class SimulationController {
    @Autowired
    private SimulationService simulationService;


    /**
     * 获取所有组件名
     * @return
     */
    @GetMapping("/names")
    public Set<String> getAllSimulationNames() {
        return simulationService.getAllSimulationInterfaceName();
    }

    /**
     * 获取该协议组件的 request Enums 列表
     *
     * @param simulationName
     * @return
     */
    @GetMapping("/requestEnums/{simulationName}")
    public List<? extends RequestEnum> getRequestEnumsBySimulationName(@PathVariable("simulationName") String simulationName) {
        return simulationService.getRequestEnumBySimulationFaceName(simulationName);
    }

    /**
     * 获取simulation组件 指定requestEnum 的参数名列表 和 参数类型列表
     *
     * @param simulationName
     * @param requestCode
     * @return
     */
    @GetMapping("/requestEnums/paramNames/{simulationName}/{requestCode}")
    public List<ParamExplainVo> getRequestEnumParmaNames(@PathVariable("simulationName") String simulationName, @PathVariable("requestCode") int requestCode) {
        return simulationService.getRequestEnumParamNames(simulationName, requestCode);
    }

    /**
     * 获取该simulation组件的 channel code 列表
     * @param simulationName
     * @return
     */
    @GetMapping("/channelCode/{simulationName}")
    public List<String> getSimulationChannelCodes(@PathVariable("simulationName") String simulationName) {
        return simulationService.getChannelCodes(simulationName);
    }

}
