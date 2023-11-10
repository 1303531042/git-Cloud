package com.ruoyi.iot.model.ThingsModels;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 物模型
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ThingsModelValueItemDto
{
    /** 物模型唯一标识符 */
    private String id;

    /** 物模型名称 */
    private String name;

    /** 物模型值 */
    private String value;

    /** 是否首页显示（0-否，1-是） */
    private Integer isTop;

    /** 是否实时监测（0-否，1-是） */
    private Integer isMonitor;
    /**
     * 是否支持图标显示（0-否，1-是）
     */
    private Integer isEcharts;

    private DataType dataType;


    @Data
    public static class DataType{
        private String type;
        private String falseText;
        private String trueText;
        private Integer maxLength;
        private String arrayType;
        private String unit;
        private BigDecimal min;
        private BigDecimal max;
        private BigDecimal step;
        private List<EnumItem> enumList;

    }

    @Data
    public static class EnumItem
    {
        private String text;
        private String value;


    }

}
