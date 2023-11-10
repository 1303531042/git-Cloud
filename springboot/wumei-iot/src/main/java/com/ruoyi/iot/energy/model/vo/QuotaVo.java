package com.ruoyi.iot.energy.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @auther: KUN
 * @date: 2023/9/27
 * @description:
 */
@Data
public class QuotaVo {
    private List<String> xAxis;
    private String xUnit;
    private Map<String, List<Double>> yDataMap;
}
