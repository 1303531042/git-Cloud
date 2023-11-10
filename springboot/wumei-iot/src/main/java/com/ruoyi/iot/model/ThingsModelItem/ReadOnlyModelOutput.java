package com.ruoyi.iot.model.ThingsModelItem;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ReadOnlyModelOutput extends ThingsModelItemBase
{
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal step;
    private String unit;
    private String arrayType;
    private String falseText;
    private String trueText;
    private int maxLength;
    private List<EnumItemOutput> enumList;




}
