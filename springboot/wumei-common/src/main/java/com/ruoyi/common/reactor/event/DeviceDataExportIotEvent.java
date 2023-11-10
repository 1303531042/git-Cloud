package com.ruoyi.common.reactor.event;

import com.ruoyi.common.reactor.selectorkey.EventSelectKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


/**
 * @auther: KUN
 * @date: 2023/6/27
 * @description: 设备数据上传事件
 */
@Getter
@Setter
@ToString
public class DeviceDataExportIotEvent extends IotEvent {
    /**
     * 物美中设备的id
     */
    private String deviceId;

    /**
     * 1= 属性上报，2：调用功能
     */
    private Integer upType;
    /**
     * 设备名
     */
    private String name;
    /**
     * 需要的具体值
     */
    private String state;
    /**
     * 写入时间
     */
    private Date createTime;

    /**
     * 标识符
     */
    private String identity;


    @Override
    protected int registerOps() {
        return EventSelectKey.OP_ACCEPCTED_DATA;
    }
}
