package com.ruoyi.gateway.server;

import com.ruoyi.gateway.server.component.SocketServerComponent;
import com.ruoyi.simulation.automessage.AutoRequestExpression;
import com.ruoyi.simulation.automessage.AutoRequestMessage;
import com.ruoyi.simulation.compoment.AbstractSimulationInterface;
import com.ruoyi.simulation.domain.SimulationDeviceWrapper;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.gateway.protocol.Protocol;
import com.ruoyi.gateway.message.SocketMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/3/29
 * @description: component为gateWay这一层提供的
 */
public abstract class AbstractGateWaySimulationInterface<T extends RequestEnum, k extends Protocol,S extends SocketMessage> extends AbstractSimulationInterface<T> {
    @Autowired
    private ServerComponentFactory serverComponentFactory;

    /**
     * 该模拟层 映射到网关层使用的组件
     */
    private SocketServerComponent serverComponent;



    public AbstractGateWaySimulationInterface() {

    }

    @PostConstruct
    public void init() {
        serverComponent = getServerComponent();
    }

    /**
     * @return 获取到该组件到message class
     */
    protected Class<S> getMessageClass() {
       return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    /**
     *
     * @return 获取到网关层所用到组件
     * @param <V>
     */
    protected  <V extends SocketServerComponent> V getServerComponent() {
        if (serverComponentFactory != null) {
            return (V) serverComponentFactory.getByClass(getMessageClass());
        }
        return null;
    }

    /**
     * 获取该模拟层所有的设备socket编号
     *
     * @return
     */
    @Override
    public List<String> getSimulationChannelCodes() {
        return serverComponent.getDeviceManager().getChannelCodes();
    }

    /**
     * 根据socket编号 获取实际socket
     *
     * @param channelCode
     * @return
     */
    @Override
    public Optional<Channel> getSimulationChannel(String channelCode) {
        return Optional.ofNullable(serverComponent.getDeviceManager().find(channelCode));

    }

    public Optional<ChannelFuture> writeAndFlush(String channelCode, Protocol protocol) {
        return serverComponent.writeAndFlush(channelCode, protocol);
    }

    /**
     * 听取请求并做出响应
     */
    @Override
    public void listenRequestThenResponse(AutoRequestMessage autoRequestMessage) {

    }
}
