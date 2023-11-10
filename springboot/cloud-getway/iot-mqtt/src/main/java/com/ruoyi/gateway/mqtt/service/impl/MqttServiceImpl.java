package com.ruoyi.gateway.mqtt.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.gateway.mqtt.service.MqttService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.client.IMqttClientMessageListener;
import net.dreamlu.iot.mqtt.spring.client.MqttClientTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author wsq
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttServiceImpl implements MqttService {

    private final MqttClientTemplate client;

    private final IMqttClientMessageListener listener;

    /**
     * 发布主题
     */
    @Override
    public boolean publish(String topic,Object message) {
        client.publish(topic, JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8));
        log.debug("发布主题：{},消息体：{}", topic, JSON.toJSONString(message));
        return true;
    }

    /**
     * 订阅主题
     */
    @Override
    public boolean sub(String topic) {
        client.subQos0(topic, listener);
        log.debug("订阅主题：{}", topic);
        return true;
    }

}