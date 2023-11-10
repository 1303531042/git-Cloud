package com.ruoyi.common.reactor.selectorkey;

import com.ruoyi.common.reactor.EventChannel;
import com.ruoyi.common.reactor.selector.EventSelector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther: KUN
 * @date: 2023/6/24
 * @description:
 */
public class NormalEventSelectKey extends EventSelectKey {
    private EventSelector eventSelector;
    private EventChannel eventChannel;
    /**
     * 该key订阅的事件合集
     */
    private Integer ops;
    /**
     * 该key 就绪的事件
     */
    private AtomicInteger readyOps;


    public NormalEventSelectKey(int ops) {
        this.ops = ops;
    }


    /**
     * 该key注册到的eventSelector
     */
    @Override
    public EventSelector eventSelector() {
        return eventSelector;
    }

    @Override
    public EventChannel eventChannel() {
        return eventChannel;
    }

    /**
     * 设置该key所属的selector
     */
    @Override
    public void setEventSelector(EventSelector eventSelector) {
        this.eventSelector = eventSelector;
    }

    /**
     * 设置该key所属的channel
     */
    @Override
    public void setEventChannel(EventChannel eventChannel) {
        this.eventChannel = eventChannel;

    }

    /**
     * 该EventSelectKey 订阅的事件位图
     *
     * @return
     */
    @Override
    public int interestOps() {
        return this.ops;
    }

    /**
     * 该EventSelectKey 订阅的事件 已就绪的事件位图，或者说是还未处理的事件合计
     *
     * @return
     */
    @Override
    public int readyOps() {
        return readyOps.get();
    }

    @Override
    public int updateReadyOps(int ops) {
        return readyOps.updateAndGet(value -> ops);
    }

    /**
     * 获取注册的事件
     * @return
     */
    @Override
    public int getRegisterOps() {
        return ops;
    }


}
