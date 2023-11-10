package com.ruoyi.common.reactor.selectorkey;


import com.ruoyi.common.reactor.EventChannel;
import com.ruoyi.common.reactor.selector.EventSelector;

/**
 * @auther: KUN
 * @date: 2023/6/13
 * @description:  EventSelectionKey 是一个表示通道注册到选择器（EventSelector）上的标记，
 * 用于表示通道已经就绪的事件类型以及通道对应的 Channel(事件发布器) 和 EventSelector 的关系。
 */
public abstract class EventSelectKey {

    /**
     * 该key注册到的eventSelector
     */
    public abstract EventSelector eventSelector();


    public abstract EventChannel eventChannel();


    /**
     * 设置该key所属的selector
     */
    public abstract void setEventSelector(EventSelector eventSelector);

    /**
     * 设置该key所属的channel
     */
    public abstract void setEventChannel(EventChannel eventChannel);





    /**
     * 数据接收事件
     */
    public static final int OP_ACCEPCTED_DATA = 1 << 0;

    /**
     * 数据检测正常事件 数据在正常范围内波动
     */
    public static final int OP_DETECTED_DATA_NORMAL = 1 << 1;

    /**
     * 数据检测异常事件  该数据点在异常范围
     */
    public static final int OP_DETECTED_DATA_EXCEPTION = 1 << 2;


    /**
     * 边缘计算处理完成事件
     */
    public static final int OP_EDGE_COMPUTING = 1 << 3;

    /**
     * 设备信息持久化 成功事件
     */
    public static final int OP_DEVICE_INFO_PERSISTENCE_SUCCESS = 1 << 4;

    /**
     * 设备信息持久化 失败
     */
    public static final int OP_DEVICE_INFO_PERSISTENCE_FAILURE = 1 << 5;


    /**
     *  该EventSelectKey 订阅的事件位图
     * @return
     */
    public abstract int interestOps();

    /**
     * 该EventSelectKey 订阅的事件 已就绪的事件位图，或者说是还未处理的事件合计
     * @return
     */
    public abstract int readyOps();

    /**
     * 接受数据事件是否就绪
     */
    public final boolean isAccepctedData(){
        return (readyOps() & OP_ACCEPCTED_DATA) != 0;
    }

    /**
     * 数据检测异常事件是否就绪
     */
    public final boolean isDetectedDataeException() {
        return (readyOps() & OP_DETECTED_DATA_EXCEPTION) != 0;
    }

    /**
     * 数据检测正常事件是否就绪
     */
    public final boolean isDetectedDataNomal() {
        return (readyOps() & OP_DETECTED_DATA_NORMAL) != 0;
    }

    /**
     * 边缘计算处理完成事件就绪
     */
    public final boolean isEngeComputing() {
        return (readyOps() & OP_EDGE_COMPUTING) != 0;
    }

    /**
     * 设备信息持久化 成功事件就绪
     */
    public final boolean isDeviceInfoPersistenceSuccess(){
        return (readyOps() & OP_DEVICE_INFO_PERSISTENCE_SUCCESS) != 0;

    }

    /**
     * 设备信息持久化 失败事件就绪
     */
    public final boolean isDevcieInfoPersistenceFailure(){
        return (readyOps() & OP_DEVICE_INFO_PERSISTENCE_FAILURE) != 0;

    }

    /**
     * 该key存储的对象
     */
    private volatile Object attachment = null;

    /**
     * 设置key携带对象
     */
    public final Object attach(Object ob){
        return this.attachment = ob;
    }

    /**
     * 获取携带对象
     */
    public final Object attachment() {
        return attachment;
    }

    /**
     * 更新该key就绪事件ops集
     * @param ops
     * @return
     */
    public abstract int updateReadyOps(int ops);

    public abstract int getRegisterOps();








}
