package com.ruoyi.iot.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/6/29
 * @description: 查询设备属性echarts 属性 前端传入参数
 */
@Data
public class HistoryDto {

    /**
     * 产品
     */
    private Long productId;
    /**
     * 设备id
     */
    private Long deviceID ;

    /**
     * 查询的起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 查询的结束事件
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 产品属性标识
     */
    private String identity;
}
