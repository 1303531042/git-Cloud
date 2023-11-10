package com.ruoyi.common.reactor;

import com.ruoyi.common.reactor.exception.channel.EventChannelIdException;
import com.ruoyi.common.reactor.selectorkey.EventSelectKey;
import lombok.Data;
import lombok.Setter;

/**
 * @auther: KUN
 * @date: 2023/6/22
 * @description: 事件通道
 */
@Data
public class EventChannel {
    /**
     * 说明该channel的作用
     */
    private final String explain;
    /**
     * channel的id
     */
    private final int channelID;

    /**
     * 该channel的事件处理器
     */
    @Setter
    private EventChannelHandler handler;

    public EventChannel(int channelID, String explain, EventChannelHandler handler) {
        this.channelID = channelID;
        this.explain = explain;
        this.handler = handler;
    }

    /**
     * 注册信息
     *
     * @param ops 事件集
     * @return 是否注册成功
     */
    public boolean register(int ops) {
        try {
            //注册到broker
            EventBroker.registerToBroker(this.channelID, ops);
            //注册到selector
            registerToSelector(ops);
        } catch (EventChannelIdException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 注册到注册到selector
     *
     * @param ops
     */
    public void registerToSelector(int ops) {
        if ((ops & EventSelectKey.OP_ACCEPCTED_DATA) != 0) {
            EventSelectKey eventSelectKey = EventBroker.getMasterEventSelector().registerKey(EventSelectKey.OP_ACCEPCTED_DATA);
            eventSelectKey.setEventChannel(this);
            eventSelectKey.attach(handler);
        }
        if ((ops & ~EventSelectKey.OP_ACCEPCTED_DATA) != 0) {
            EventSelectKey eventSelectKey = EventBroker.getSlaveEventSelector().registerKey(ops & ~EventSelectKey.OP_ACCEPCTED_DATA);
            eventSelectKey.setEventChannel(this);
            eventSelectKey.attach(handler);
        }
    }

    @Override
    public String toString() {
        return "channelID: " + channelID + ", channel作用：" + explain;
    }


}
