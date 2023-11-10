package com.ruoyi.iot.model.bo;

import com.ruoyi.iot.domain.Device;
import com.ruoyi.iot.domain.DeviceLogBO;
import com.ruoyi.iot.influx.NormalMeasurement;
import lombok.Data;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/14
 * @description:
 */
@Data
public class StreamReportBo {
    private Device device;
    private NormalMeasurement normalMeasurement;
    private DeviceLogBO deviceLog;
    private Date eventTime;
 }
