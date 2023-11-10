package com.ruoyi.iot.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/7/2
 * @description: 查询device 事件 前端参数DTO
 */
@Data
public class DeviceEventDto {
    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 标识符
     */
    private String identity;
    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
