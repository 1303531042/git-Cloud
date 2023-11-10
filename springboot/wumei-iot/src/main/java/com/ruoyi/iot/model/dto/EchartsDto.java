package com.ruoyi.iot.model.dto;

import com.ruoyi.iot.model.MonitorModel;
import lombok.Data;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/6/29
 * @description:
 */
@Data
public class EchartsDto {
    private String unit;
    private String identifier;
    private String name;
    private List<MonitorModel> monitorModels;
}
