package com.ruoyi.common.reactor.selector;

import com.ruoyi.common.reactor.selectorkey.EventSelectKey;
import com.ruoyi.common.reactor.selectorkey.NormalEventSelectKey;

import java.util.Set;

/**
 * @auther: KUN
 * @date: 2023/6/22
 * @description: 事件选择器
 */
public abstract class EventSelector {


    /**
     * 返回当前所有注册在selector中channel的selectionKey
     */
    public abstract Set<EventSelectKey> keys();

    /**
     * 返回注册在selector中等待IO操作(及有事件发生)channel的selectionKey。
     */
    public abstract Set<EventSelectKey> selectKeys();

    /**
     * 遍历一次注册进来的channel 是否有事件就绪，立即返回
     * @return 就是channel的个数
     */
    public abstract int selectNow();

    /**
     * 循环遍历注册进来的channel 是否有事件就绪，直到有channel事件就绪，或者超过时间
     * @return 就是channel的个数
     */
    public abstract int select(long timeout);


    /**
     * 循环遍历注册进来的channel 是否有事件就绪，直到有channel事件就绪
     * @return 就是channel的个数
     */
    public abstract void select();


    /**
     * close为true 跳出循环体 线程结束
     */
    public abstract void close();


    /**
     * 注册key
     *
     * @param eventSelectKey
     */
    public abstract void addRegisterKey(EventSelectKey eventSelectKey);
    /**
     * 添加注册进来的key
     *
     * @param ops
     * @return
     */
    public EventSelectKey registerKey(int ops) {
        EventSelectKey eventSelectKey = new NormalEventSelectKey(ops);
        eventSelectKey.setEventSelector(this);
        addRegisterKey(eventSelectKey);
        updateMonitorOps(ops);
        return eventSelectKey;

    }

    /**
     * 获取该selector监听的ops
     * @return
     */
    public abstract int getMonitorOps();

    /**
     * 设置该selector监听的ops
     * @return
     */
    public abstract int updateMonitorOps(int ops);



}
