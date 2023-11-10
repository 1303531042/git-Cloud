package com.ruoyi.iot.energy.model.vo;

import lombok.Data;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/12
 * @description:
 */
@Data
public class AreaStatisticVo {
    private List<String> xAxis;
    private String xUnit;
    private List<EchartsVo> echartsVos;

}

