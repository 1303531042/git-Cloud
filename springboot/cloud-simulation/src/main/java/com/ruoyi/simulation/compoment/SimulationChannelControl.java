package com.ruoyi.simulation.compoment;

import io.netty.channel.Channel;
import java.util.List;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/7/25
 * @description: 模拟设备socket接口 获取socket 管理socket的生命周期
 */
public interface SimulationChannelControl{
    /**
     * 获取该模拟层所有的设备socket编号
     * @return
     */
    List<String> getSimulationChannelCodes();

    /**
     * 根据socket编号 获取实际socket
     * @param channelCode
     * @return
     */
    Optional<Channel> getSimulationChannel( String channelCode);
}
