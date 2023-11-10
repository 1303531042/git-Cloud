package com.ruoyi.common.reactor.selector;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.ruoyi.common.reactor.EventBroker;
import com.ruoyi.common.reactor.EventChannelHandler;
import com.ruoyi.common.reactor.event.DeviceDataExportIotEvent;
import com.ruoyi.common.reactor.event.IotEvent;
import com.ruoyi.common.reactor.selectorkey.EventSelectKey;
import com.ruoyi.common.reactor.thread.SelectorThreadPool;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther: KUN
 * @date: 2023/6/23
 * @description:  EventSelector 实现类
 */
public class NormalEventSelector extends EventSelector {

    private boolean close = false;
    /**
     * 该selector 监听的事件
     */
    private AtomicInteger monitorOps = new AtomicInteger(0);

    /**
     * 存储注册进来的EventSelectKey
     */
    private final Set<EventSelectKey> registerKey = new ConcurrentHashSet<>();

    /**
     * 执行select函数的线程工厂
     */
    private final SelectorThreadPool selectorThreadFactory = new SelectorThreadPool();


    /**
     * 返回当前所有注册在selector中channel的selectionKey
     */
    @Override
    public Set<EventSelectKey> keys() {
        return this.registerKey;
    }

    /**
     * 返回注册在selector中等待IO操作(及有事件发生)channel的selectionKey。
     */
    @Override
    public Set<EventSelectKey> selectKeys() {
        return null;
    }

    /**
     * 遍历一次注册进来的channel 是否有事件就绪，立即返回
     *
     * @return 就是channel的个数
     */
    @Override
    public int selectNow() {
        return 0;
    }

    /**
     * 循环遍历注册进来的channel 是否有事件就绪，直到有channel事件就绪，或者超过时间
     *
     * @param timeout
     * @return 就是channel的个数
     */
    @Override
    public int select(long timeout) {
        return 0;
    }

    /**
     * 循环遍历注册进来的channel 是否有事件就绪，直到有channel事件就绪
     *
     * @return 就是channel的个数
     */
    @Override
    public void select() {
        selectorThreadFactory.execute(() -> {
            long time = System.currentTimeMillis();

            while (!close) {
                if (System.currentTimeMillis() > time) {
                    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                    try {
                        selectorThreadFactory.execute(() -> {
                            int ops = monitorOps.get() & EventBroker.getReadyEventBitMap();
                            if (ops != 0) {
                                registerKey.forEach(key->{
                                    try {
                                        if ((key.getRegisterOps() & ops) != 0) {
                                            List<IotEvent> eventList = EventBroker.getEventList(key.eventChannel().getChannelID(), key.interestOps());
                                            Object attachment = key.attachment();
                                            if (attachment instanceof EventChannelHandler) {
                                                EventChannelHandler eventChannelHandler = (EventChannelHandler) attachment;
                                                eventChannelHandler.handler(eventList);

                                            }
                                            atomicBoolean.set(true);


                                        }else {
                                            atomicBoolean.set(false);

                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                });
                            }
                        });

                        if (atomicBoolean.get()) {
                            time += 500;
                            Thread.sleep(500);

                        } else {
                            time += 500;
                            Thread.sleep(500);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }

    /**
     * close为true 跳出循环体 线程结束
     */
    @Override
    public void close() {
        close = true;
    }

    /**
     * 注册key
     *
     * @param eventSelectKey
     */
    @Override
    public void addRegisterKey(EventSelectKey eventSelectKey) {
        registerKey.add(eventSelectKey);
    }

    /**
     * 获取该selector监听的ops
     *
     * @return
     */
    @Override
    public int getMonitorOps() {
        return monitorOps.get();
    }

    /**
     * 设置该selector监听的ops
     *
     * @param ops
     * @return
     */
    @Override
    public int updateMonitorOps(int ops) {
        return monitorOps.updateAndGet(value -> value | ops);
    }


}
