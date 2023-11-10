package com.ruoyi.simulation.service;

import com.ruoyi.simulation.domain.vo.ParamExplainVo;
import com.ruoyi.simulation.enums.RequestEnum;
import io.netty.channel.Channel;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @auther: KUN
 * @date: 2023/4/17
 * @description:  模拟层服务类
 */
public interface SimulationService {

    Set<String> getAllSimulationInterfaceName();

    List<? extends RequestEnum> getRequestEnumBySimulationFaceName(String simulationName);


    List<ParamExplainVo> getRequestEnumParamNames(String simulationName, int requestCode);

    List<String> getChannelCodes(String simulationName);

    Optional<Channel> getChannel(String simulationName, String channelCode);
}
