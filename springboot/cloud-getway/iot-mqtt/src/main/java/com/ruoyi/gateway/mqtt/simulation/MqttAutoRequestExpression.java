package com.ruoyi.gateway.mqtt.simulation;

import com.ruoyi.simulation.automessage.AutoRequestExpression;
import com.ruoyi.simulation.automessage.AutoRequestExpressionRegistry;
import com.ruoyi.simulation.manager.SimulationDeviceManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @auther: KUN
 * @date: 2023/8/17
 * @description: mqtt 数据匹配表达式
 */
@Data
@Component
public class MqttAutoRequestExpression implements AutoRequestExpression {

    public MqttAutoRequestExpression() {
        AutoRequestExpressionRegistry.registerImplementation(this.getClass().getName(), this.getClass());
    }
    /**
     * mqtt主题
     */
    private String topic;
    /**
     * json表达式
     */
    private String jsonExpression;

}
