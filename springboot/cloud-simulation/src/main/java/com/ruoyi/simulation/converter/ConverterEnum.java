package com.ruoyi.simulation.converter;

/**
 * 转换器枚举
 */
public enum ConverterEnum {

    IntegerConverter(new IntegerConverter(), "integer"),
    DecimalConverter(new DecimalConverter(), "decimal"),
    EnumConverter(new EnumConverter(), "enum"),
    BoolConverter(new BoolConverter(), "bool"),
    StringConverter(new StringConverter(),"string");
    private final ParameterConverter converter;
    private final String converterType;

    ConverterEnum(ParameterConverter converter, String converterType) {
        this.converter = converter;
        this.converterType = converterType;
    }


    public static ParameterConverter getConverterByType(String converterType) {
        for (ConverterEnum converterEnum : ConverterEnum.values()) {
            if (converterEnum.converterType.equals(converterType)) {
                return converterEnum.converter;
            }
        }
        return null; // 或者抛出异常，表示不支持的转换器类型
    }

}
