package com.ruoyi.gateway.mqtt.listener;

import com.ruoyi.gateway.mqtt.simulation.MqttAutoRequestMessage;
import com.ruoyi.simulation.compoment.SimulationInterface;
import com.ruoyi.simulation.manager.SimulationInterfaceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.client.IMqttClientMessageListener;
import org.springframework.stereotype.Component;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 订阅消息监听类
 *
 * @author kun
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttClientMessageListener implements IMqttClientMessageListener {
    private final SimulationInterfaceManager simulationInterfaceManager;
    @Override
    public void onMessage(String topic, ByteBuffer payload) {
        byte[] bs = payload.array();
        String message = new String(bs, StandardCharsets.UTF_8);
        MqttAutoRequestMessage autoRequestMessage = new MqttAutoRequestMessage();
        autoRequestMessage.setMessage(message);
        autoRequestMessage.setTopic(topic);
        SimulationInterface<?> simulation = simulationInterfaceManager.getSimulationForName("mqtt");
        simulation.listenRequestThenResponse(autoRequestMessage);
    }


}
