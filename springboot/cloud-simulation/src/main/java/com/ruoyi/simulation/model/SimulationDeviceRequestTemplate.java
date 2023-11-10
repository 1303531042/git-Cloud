package com.ruoyi.simulation.model;

import com.ruoyi.simulation.compoment.SimulationInterface;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.simulation.enums.TypeEnum;
import lombok.Data;

import java.util.Map;

/**
 * @auther: KUN
 * @date: 2023/4/25
 * @description: 设备发送请求所需参数存储
 */
@Data
public class SimulationDeviceRequestTemplate {
    /**
     * 设备编号
     */
    private String deviceCode;
    /**
     * channel 编号
     */
    private String channelCode;
    /**
     * 属性名/方法名/事件名
     */
    private String name;
    /**
     * 该属性返回值类型
     */
    private TypeEnum resultType;
    /**
     * 所属simulation interface
     */
    private SimulationInterface<?> simulationInterface;
    /**
     *  请求方法 requestEnum
     */
    private RequestEnum requestEnum;
    /**
     * 参数 map
     */
    private Map<Integer, Object> paramsMap;



}
