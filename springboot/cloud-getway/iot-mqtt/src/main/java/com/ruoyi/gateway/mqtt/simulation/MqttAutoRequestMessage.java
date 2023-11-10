package com.ruoyi.gateway.mqtt.simulation;

import com.ruoyi.simulation.automessage.AutoRequestExpression;
import com.ruoyi.simulation.automessage.AutoRequestMessage;
import lombok.Data;

import java.util.Objects;

/**
 * @auther: KUN
 * @date: 2023/8/17
 * @description: mqtt RequestAutoMessage实现
 */
@Data
public class MqttAutoRequestMessage implements AutoRequestMessage {
    /**
     * mqtt主题
     */
    private String topic;
    /**
     * 此次发送的消息
     */
    private String message;



    /**
     * 网关发送数据 属于哪个设备
     */
    @Override
    public boolean matchDevice(AutoRequestExpression expression) {
        if (expression instanceof MqttAutoRequestExpression) {
            MqttAutoRequestExpression mqttAutoRequestExpression = (MqttAutoRequestExpression) expression;
            return Objects.equals(mqttAutoRequestExpression.getTopic(), topic);
        }
        return false;
    }
}
