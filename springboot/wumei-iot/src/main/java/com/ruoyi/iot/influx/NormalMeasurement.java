package com.ruoyi.iot.influx;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.ruoyi.common.influxdb.Bucket;
import com.ruoyi.common.influxdb.ColumnType;
import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.kv.DataType;
import com.sun.org.apache.xpath.internal.operations.Equals;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

/**
 * @auther: KUN
 * @date: 2023/2/22
 * @description: 通用influxdb表
 */
@Getter
@Setter
@Bucket("IotCloud")
@Measurement(name = "device")
public class NormalMeasurement implements Measudirection {
    /**
     * 区域id
     */
    @Column(tag = true)
    private Long areaId;
    /**
     * 产品id
     */
    @Column(tag = true)
    private Long productId;

    /**
     * 设备名（主键）
     */
    @Column(tag = true)
    private Long deviceID;

    /**
     * 设备属性
     */
    @Column(tag = true)
    private String devField;
    /**
     * 窗口聚合方式 0-last 1-mean 2-max 3-min
     */
    @Column(tag = true)
    private Integer aggregation;

    /**
     * 设备发送信息时间
     */
    @Column(timestamp = true)
    private Instant createTime;

    /**
     * 值
     */
    @Column
    @ColumnType(type = DataType.DOUBLE)
    private Double value;


    @Override
    public boolean isSimilar(Measudirection measudirection) {
        if (this == measudirection) return true;
        if (measudirection == null || getClass() != measudirection.getClass()) return false;
        NormalMeasurement that = (NormalMeasurement) measudirection;
        return (that.areaId == null || Objects.equals(areaId, that.areaId)) && (that.productId == null || Objects.equals(productId, that.productId)) && (that.deviceID == null || Objects.equals(deviceID, that.deviceID)) && (that.devField == null || Objects.equals(devField, that.devField)) && (that.aggregation == null || Objects.equals(aggregation, that.aggregation));
    }
}
