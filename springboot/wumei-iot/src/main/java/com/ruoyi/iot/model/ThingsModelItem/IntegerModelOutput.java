package com.ruoyi.iot.model.ThingsModelItem;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class IntegerModelOutput extends ThingsModelItemBase
{
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal step;
    private String unit;

}
