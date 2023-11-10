package com.ruoyi.gateway.mqtt.listener;

import com.ruoyi.gateway.mqtt.simulation.TopicManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.codec.MqttQoS;
import net.dreamlu.iot.mqtt.spring.client.MqttClientTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * spring容器启动后触发ApplicationReadyEvent
 * @author alanwake
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartedCommandLineRunner implements ApplicationListener<ApplicationReadyEvent> {

    private final MqttClientTemplate client;

    private final MqttClientMessageListener messageListener;

    private final TopicManager topicManager;



    /**
     * spring容器启动后触发ApplicationReadyEvent 进行连接，订阅主题
     * @param event
     */
    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        if (!client.isConnected()) {
            client.reconnect();
        }
        String[] topics = topicManager.getTopics();
        log.debug("订阅主题:{}", topics);
        client.subscribe(topics, MqttQoS.AT_LEAST_ONCE, messageListener);
    }
}
