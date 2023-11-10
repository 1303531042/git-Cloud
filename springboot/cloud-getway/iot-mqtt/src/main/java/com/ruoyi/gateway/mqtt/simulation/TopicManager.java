package com.ruoyi.gateway.mqtt.simulation;

import com.ruoyi.simulation.automessage.AutoRequestExpression;
import com.ruoyi.simulation.manager.SimulationDeviceManager;
import com.ruoyi.simulation.manager.SimulationInterfaceManager;
import com.ruoyi.simulation.model.DeviceCodeAndProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @auther: KUN
 * @date: 2023/8/21
 * @description:
 */
@Component
@RequiredArgsConstructor
public class TopicManager {
    private final SimulationInterfaceManager simulationInterfaceManager;


    private final SimulationDeviceManager simulationDeviceManager;

    private final Set<String> topicSet = new HashSet<>();

    /**
     * key: topic
     * value: expression列表
     */
    private final Map<String, List<MqttAutoRequestExpression>> topicExpressionMap = new HashMap<>();
    private boolean hadInit = false;

    public void init() {
        List<Integer> productIds = simulationInterfaceManager.getProductIdsForSimulationName("mqtt");
        productIds.forEach(productId -> {
            simulationDeviceManager.getDeviceForProductID(productId).forEach(device -> {
                String deviceCode = device.getDeviceCode();
                Map<String, AutoRequestExpression> expressionMap = simulationDeviceManager.getExpressionForDevcieCode(deviceCode);
                expressionMap.forEach((key, value) -> {
                    if (value instanceof MqttAutoRequestExpression) {
                        MqttAutoRequestExpression mqttExpression = (MqttAutoRequestExpression) value;
                        String topic = mqttExpression.getTopic();
                        topicSet.add(topic);
                        List<MqttAutoRequestExpression> mqttAutoRequestExpressions = topicExpressionMap.computeIfAbsent(topic, k -> new ArrayList<>());
                        mqttAutoRequestExpressions.add(mqttExpression);
                    } else {
                        throw new RuntimeException();
                    }

                });
            });
        });
    }

    public  DeviceCodeAndProperty getDeviceCodeAndPropertyForexpression(MqttAutoRequestExpression expression) {
        if (!hadInit) {
            hadInit = true;
            init();
        }
        return simulationDeviceManager.getDeviceCodeAndPropertyForExpression(expression);
    }

    public List<MqttAutoRequestExpression> getMqttAutoRequestExpressionsForTopic(String topic) {
        if (!hadInit) {
            hadInit = true;
            init();
        }
        return topicExpressionMap.get(topic);
    }

    public String[] getTopics() {
        if (!hadInit) {
            hadInit = true;
            init();
        }
        return topicSet.toArray(new String[0]);
    }

}
