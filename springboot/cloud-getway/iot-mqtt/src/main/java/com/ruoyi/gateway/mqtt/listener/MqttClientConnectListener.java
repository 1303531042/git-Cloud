package com.ruoyi.gateway.mqtt.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.client.IMqttClientConnectListener;
import net.dreamlu.iot.mqtt.core.client.MqttClientCreator;
import net.dreamlu.iot.mqtt.spring.client.config.MqttClientProperties;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;

/**
 * 客户端链接事件
 *
 * @author kun
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttClientConnectListener implements IMqttClientConnectListener {
    private final MqttClientCreator mqttClientCreator;
    private final MqttClientProperties mqttClientProperties;


    @Override
    public void onConnected(ChannelContext channelContext, boolean isReconnect) {
        log.info("MQTT订阅服务连接成功");
    }

    @Override
    public void onDisconnect(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
        log.info("断开链接：{}" + remark);
        mqttClientCreator.clientId(mqttClientProperties.getClientId() + System.currentTimeMillis())
            .username(mqttClientProperties.getUserName())
            .password(mqttClientProperties.getPassword());
        log.info("MQTT订阅服务正在重新连接");
    }
}
