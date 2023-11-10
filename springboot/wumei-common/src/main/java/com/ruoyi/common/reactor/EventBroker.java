package com.ruoyi.common.reactor;

import com.ruoyi.common.reactor.event.DeviceDataExportIotEvent;
import com.ruoyi.common.reactor.event.IotEvent;
import com.ruoyi.common.reactor.event.IotEventWrapper;
import com.ruoyi.common.reactor.exception.channel.EventChannelIdException;
import com.ruoyi.common.reactor.selector.EventSelector;
import com.ruoyi.common.reactor.selector.NormalEventSelector;
import com.ruoyi.common.reactor.thread.BrokerClearThreadPool;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @auther: KUN
 * @date: 2023/6/23
 * @description: 事件中心类
 */
public class EventBroker {

    /**
     * accepted_data事件选择器
     */
    private static final EventSelector MASTER_EVENT_SELECTOR = new NormalEventSelector();
    /**
     * 其他事件选择器
     */
    private static final EventSelector SLAVE_EVENT_SELECTOR = new NormalEventSelector();

    public static EventSelector getMasterEventSelector() {
        return MASTER_EVENT_SELECTOR;
    }
    public static EventSelector getSlaveEventSelector() {
        return SLAVE_EVENT_SELECTOR;
    }


    /**
     * 注册的channelID位图
     */
    private static final AtomicInteger CHANNEL_ID_REGISTER_BIT_MAP = new AtomicInteger(0);

    /**
     * key: 事件id
     * value: 订阅该事件的channel 位图
     */
    private static final Map<Integer, AtomicInteger> SUB_EVENT_CHANNEL_MAP = new ConcurrentHashMap<>();


    /**
     * 就绪的事件位图
     */
    private static final AtomicInteger READY_EVENT_BIT_MAP = new AtomicInteger(0);

    /**
     * 清洁队列线程工厂
     */
    private static final BrokerClearThreadPool BROKER_CLEAR_THREAD_POOL = new BrokerClearThreadPool();

    /**
     * key: 事件ops
     * value:  事件队列
     */
    private static final Map<Integer, Queue<IotEventWrapper>> EVENT_QUEUE_MAP = new ConcurrentHashMap<>();


    /**
     * 事件异常队列
     */
    private static final Queue<IotEventWrapper> exceptionEventQueue = new ConcurrentLinkedQueue<>();

    static {
        //加载该类后每三十秒清洁一次队列
        clearStart(60000);
        MASTER_EVENT_SELECTOR.select();
        SLAVE_EVENT_SELECTOR.select();
    }


    /**
     * 发布事件并更新 ops
     *
     * @param iotEvent
     */
    public static synchronized void publishEvent(IotEvent iotEvent) {

        IotEventWrapper iotEventWrapper = new IotEventWrapper();
        iotEventWrapper.setIotEvent(iotEvent);
        AtomicInteger atomicInteger = SUB_EVENT_CHANNEL_MAP.computeIfAbsent(iotEvent.getEventOps(), Key -> new AtomicInteger(0));
        iotEventWrapper.setChannelIDOps(new AtomicInteger(atomicInteger.get()));

        Queue<IotEventWrapper> queue = EVENT_QUEUE_MAP.computeIfAbsent(iotEvent.getEventOps(), key -> new ConcurrentLinkedQueue<>());
        queue.offer(iotEventWrapper);
        READY_EVENT_BIT_MAP.updateAndGet(value -> value | iotEvent.getEventOps());
    }





    /**
     * 注册到broker
     * @param channelID
     * @param ops
     * @throws EventChannelIdException
     */
    public static void registerToBroker(int channelID, int ops) throws EventChannelIdException {
        registerChannelID(channelID);
        registerChannelOps(channelID, ops);
    }

    /**
     * 在broker中注册channelID
     * @param channelID
     * @throws EventChannelIdException
     */
    private static void registerChannelID(int channelID) throws EventChannelIdException{
        CHANNEL_ID_REGISTER_BIT_MAP.getAndUpdate(x ->{
            if ((x & channelID) != 0) {
                throw new EventChannelIdException(channelID + "已存在");
            }
            return x | channelID;
        });
    }

    /**
     * 在broker中注册channelID 订阅的事件合集
     *
     * @param channelID channel的 id
     * @param ops       事件合集
     */
    private static void registerChannelOps(int channelID, int ops) {
        AtomicInteger atomicInteger = SUB_EVENT_CHANNEL_MAP.computeIfAbsent(ops, Key -> new AtomicInteger(0));
        atomicInteger.updateAndGet(value -> value | channelID);
    }

    /**
     * 每三十秒清洁一次队列
     */
    private static void clearStart(long time) {
        BROKER_CLEAR_THREAD_POOL.execute(() -> {
            AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis()+time);
            while (true) {
                try {
                    if (System.currentTimeMillis() > atomicLong.get()) {
                        clear();
                        atomicLong.updateAndGet(value -> value + time);
                        Thread.sleep(time);
                    } else {
                        Thread.sleep(time/4);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 清洁队列方法
     */
    private static synchronized void clear() {
        Iterator<Map.Entry<Integer, Queue<IotEventWrapper>>> iterator = EVENT_QUEUE_MAP.entrySet().iterator();
        AtomicInteger ops = new AtomicInteger(0);
        while (iterator.hasNext()) {
            Map.Entry<Integer, Queue<IotEventWrapper>> entry = iterator.next();
            entry.getValue().removeIf(iotEventWrapper -> iotEventWrapper.getChannelIDOps().get() == 0);

            if (entry.getValue().size() == 0) {
                iterator.remove();
            } else {
                ops.updateAndGet(value -> value | entry.getKey());
            }
            READY_EVENT_BIT_MAP.set(ops.get());

        }

    }

    public static int getReadyEventBitMap() {
        return READY_EVENT_BIT_MAP.get();
    }

    /**
     * 获取对应事件的队列
     *
     * @param ops
     * @return
     */
    public static synchronized List<IotEvent> getEventList(int channelID,int ops) {
        List<IotEvent> result = new CopyOnWriteArrayList<>();
        EVENT_QUEUE_MAP.forEach((key, value)->{
            if ((key & ops) != 0) {
                value.stream()
                        .filter(iotEventWrapper -> (iotEventWrapper.getChannelIDOps().get() & channelID) != 0)
                        .forEach(iotEventWrapper -> {
                            iotEventWrapper.getChannelIDOps().updateAndGet(i -> i & ~channelID);
                            result.add(iotEventWrapper.getIotEvent());
                        });
            }

        });
        return result;
    }











}

