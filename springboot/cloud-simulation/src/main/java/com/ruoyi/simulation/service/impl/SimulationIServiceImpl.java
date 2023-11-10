package com.ruoyi.simulation.service.impl;

import com.ruoyi.simulation.compoment.SimulationInterface;
import com.ruoyi.simulation.domain.vo.ParamExplainVo;
import com.ruoyi.simulation.manager.SimulationInterfaceManager;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.simulation.service.SimulationService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description: 模拟层服务实现类
 */
@Service
public class SimulationIServiceImpl implements SimulationService {
    @Autowired
    private  SimulationInterfaceManager simulationInterfaceManager;


    /**
     *
     * @return 获取所有simulationInterface 名字
     */
    @Override
    public Set<String> getAllSimulationInterfaceName() {
        return simulationInterfaceManager.getSimulationNames();
    }

    /**
     * @param simulationName simulationInterface 名字
     * @return 获取该模拟组件的所有 requestEnum
     */
    @Override
    public List<? extends RequestEnum> getRequestEnumBySimulationFaceName(String simulationName) {
        return simulationInterfaceManager.getResultEnumsForSimulationName(simulationName);
    }


    /**
     * 获取该requestEnum 参数名列表
     * @param simulationName
     * @return
     */
    @Override
    public List<ParamExplainVo> getRequestEnumParamNames(String simulationName, int requestCode) {
        SimulationInterface<?> simulationInterface = simulationInterfaceManager.getSimulationForName(simulationName);
        RequestEnum requestEnum = simulationInterfaceManager.getRequestEnum(simulationName, requestCode);
        return null;
//        return simulationInterface.getParamExplainVosBeforeInstance(requestEnum);
    }

    @Override
    public List<String> getChannelCodes(String simulationName) {
//        SimulationInterface<?> simulation = simulationInterfaceManager.getSimulationForName(simulationName);
//        return simulation.getSimulationChannelCodes();
        return null;
    }

    @Override
    public Optional<Channel> getChannel(String simulationName, String channelCode) {
//        SimulationInterface<?> simulation = simulationInterfaceManager.getSimulationForName(simulationName);
//        return simulation.getSimulationChannel(channelCode);
        return null;

    }


}
