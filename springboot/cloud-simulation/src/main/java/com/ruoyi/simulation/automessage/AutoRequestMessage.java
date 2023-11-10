package com.ruoyi.simulation.automessage;

/**
 * @auther: KUN
 * @date: 2023/8/17
 * @description: 设备主动向网关发送数据
 */
public interface AutoRequestMessage {
    /**
     * 网关发送数据 属于哪个设备
     */
    boolean matchDevice(AutoRequestExpression expression);
}
