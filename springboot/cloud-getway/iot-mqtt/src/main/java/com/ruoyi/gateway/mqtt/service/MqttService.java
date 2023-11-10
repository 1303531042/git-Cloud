package com.ruoyi.gateway.mqtt.service;

/**
 * @auther: KUN
 * @date: 2023/8/17
 * @description: mqtt服务接口
 */
public interface MqttService {
    /**
     * 发布主题
     */
    boolean publish(String topic, Object message);

    /**
     * 订阅主题
     */
    boolean sub(String topic);
}
