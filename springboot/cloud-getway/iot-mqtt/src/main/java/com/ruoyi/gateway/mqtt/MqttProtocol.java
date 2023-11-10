package com.ruoyi.gateway.mqtt;

import com.ruoyi.gateway.mqtt.service.MqttService;
import com.ruoyi.gateway.mqtt.simulation.MqttSimulationInterfaceImpl;
import com.ruoyi.simulation.annotations.SimulationClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @auther: KUN
 * @date: 2023/8/17
 * @description:
 */
@Component
@SimulationClass(MqttSimulationInterfaceImpl.class)
public class MqttProtocol implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static MqttService mqttService;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        MqttProtocol.applicationContext = applicationContext;
    }


    private static void invokeNonStaticMethod() {
        if (mqttService == null) {
            mqttService = applicationContext.getBean(MqttService.class);
        }
        mqttService.sub("A");

    }
}
