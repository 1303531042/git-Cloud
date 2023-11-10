package com.ruoyi.common.reactor;


import com.ruoyi.common.reactor.event.DeviceDataExportIotEvent;
import com.ruoyi.common.reactor.event.IotEvent;
import com.ruoyi.common.reactor.thread.EventHandlerThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * @auther: KUN
 * @date: 2023/6/24
 * @description: 通道事件处理器
 */
@Slf4j
public abstract class EventChannelHandler {

    private static final EventHandlerThreadPool EVENT_HANDLER_THREAD_POOL = new EventHandlerThreadPool();


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create AbstractQueryTemplateFluxWrapper thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void handler(List<IotEvent> iotEventList) {
        stream(iotEventList).forEach(iotEvents->{
            EVENT_HANDLER_THREAD_POOL.execute(() -> process(iotEvents));
        });


    }

    protected abstract void process(List<IotEvent> iotEventList);

    /**
     * 事件分流 批处理
     * @param iotEventList
     * @return
     */
    protected abstract List<List<IotEvent>> stream(List<IotEvent> iotEventList);

}

