package com.ruoyi.common.reactor.event;

import lombok.Getter;

/**
 * @auther: KUN
 * @date: 2023/6/23
 * @description: 事件接口
 */
public abstract class IotEvent {


    @Getter
    private int eventOps;

    public IotEvent() {
        setEventOps(registerOps());
    }

    protected abstract int registerOps();

    protected void setEventOps(int ops) {
        this.eventOps = ops;
    }

}
