package com.ruoyi.gateway.mqtt.simulation;

import com.ruoyi.common.reactor.EventBroker;
import com.ruoyi.common.reactor.event.DeviceDataExportIotEvent;
import com.ruoyi.common.utils.JSONUtils;
import com.ruoyi.gateway.mqtt.simulation.enums.MqttMethodEnum;
import com.ruoyi.simulation.automessage.AutoRequestMessage;
import com.ruoyi.simulation.compoment.AbstractSimulationInterface;
import com.ruoyi.simulation.domain.SimulationDeviceWrapper;
import com.ruoyi.simulation.model.DeviceCodeAndProperty;
import com.ruoyi.simulation.model.SimulationDeviceRequestTemplate;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @auther: KUN
 * @date: 2023/8/17
 * @description: mqtt simulation 实现类
 */
@Component("mqtt")
@RequiredArgsConstructor
public class MqttSimulationInterfaceImpl extends AbstractSimulationInterface<MqttMethodEnum> {

    private final TopicManager topicManager;

    /**
     * 返回扫描 simulation interface类所在的包名
     */
    @Override
    protected String checkPackageName() {
        return "com.ruoyi.gateway.mqtt";
    }

    /**
     * 处理请求的返回值
     *
     * @param template
     * @param result
     */
    @Override
    public void handlerResponse(SimulationDeviceRequestTemplate template, Object result) {
    }

    /**
     * 获取该模拟层所有的设备socket编号
     *
     * @return
     */
    @Override
    public List<String> getSimulationChannelCodes() {
        return null;
    }

    /**
     * 根据socket编号 获取实际socket
     *
     * @param channelCode
     * @return
     */
    @Override
    public Optional<Channel> getSimulationChannel(String channelCode) {
        return Optional.empty();
    }

    /**
     * 获取该模拟类名字
     *
     * @return
     */
    @Override
    public String getSimulationName() {
        return "mqtt";
    }

    /**
     * 听取请求并做出响应
     */
    @Override
    public synchronized void listenRequestThenResponse(AutoRequestMessage autoRequestMessage) {

        if (autoRequestMessage instanceof MqttAutoRequestMessage) {
            MqttAutoRequestMessage mqttRequestAutoMessage = (MqttAutoRequestMessage) autoRequestMessage;
            List<MqttAutoRequestExpression> expressionList = topicManager.getMqttAutoRequestExpressionsForTopic(mqttRequestAutoMessage.getTopic());
            expressionList.stream()
                    .filter(mqttRequestAutoMessage::matchDevice)
                    .forEach(expression -> {
                        String jsonExpression = expression.getJsonExpression();
                        AtomicReference<String> data = new AtomicReference<>();
                        data.set(JSONUtils.getData(mqttRequestAutoMessage.getMessage(), jsonExpression));
                        if (data.get() != null) {
                            DeviceCodeAndProperty deviceCodeAndProperty = topicManager.getDeviceCodeAndPropertyForexpression(expression);
                            SimulationDeviceWrapper device = deviceCodeAndProperty.getDeviceWrapper();
                            DeviceDataExportIotEvent deviceDataExportIotEvent = new DeviceDataExportIotEvent();
                            deviceDataExportIotEvent.setDeviceId(device.getId().toString());
                            deviceDataExportIotEvent.setState(data.get());
                            deviceDataExportIotEvent.setName(device.getDeviceName());
                            deviceDataExportIotEvent.setCreateTime(new Date());
                            deviceDataExportIotEvent.setIdentity(deviceCodeAndProperty.getProperty());
                            deviceDataExportIotEvent.setUpType(1);
                            EventBroker.publishEvent(deviceDataExportIotEvent);
                        }
                    });
        } else {
            throw new RuntimeException("autoRequestMessage to MqttAutoRequestMessage 造型失败 =====>" +autoRequestMessage);
        }


    }
}
