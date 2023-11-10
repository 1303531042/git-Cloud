package com.ruoyi.iot.energy.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/10/16
 * @description:
 */
@Data
public class SimpleDeviceTemplateVo {

    /**
     * 设备名称
     */
    @ExcelProperty(value="设备名称", index=0)
    private String deviceName;

    /**
     * 设备经度
     */
    @ExcelProperty(value="设备经度", index=1)
    private Double longitude;

    /**
     * 设备纬度
     */
    @ExcelProperty(value="设备纬度", index=2)
    private Double latitude;
}
