package com.ruoyi.iot.energy.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/12
 * @description:
 */
@Data
public class EchartsVo {
    private String Name;
    private List<Double> yDataList;


}
