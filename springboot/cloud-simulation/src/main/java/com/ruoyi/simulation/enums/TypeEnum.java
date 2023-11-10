package com.ruoyi.simulation.enums;


public enum TypeEnum {
    BooleanEnum("boolean", 0, Boolean.class),
    ShortEnum("short", 1, Short.class),
    IntegerEnum("integer", 2, Integer.class),
    LongEnum("long", 3, Long.class),
    CharEnum("char", 4, Character.class),
    StringEnum("string", 5, String.class),
    FloatEnum("float", 6, Float.class),
    DoubleEnum("double", 7, Double.class),
    ByteEnum("byte", 8, Byte.class),
    errorEnum("error", 9, null),

    ;
    private final String type;
    private final int code;
    private final Class<?> typeClass;

    TypeEnum(String type, int code, Class<?> typeClass) {
        this.type = type;
        this.code = code;
        this.typeClass = typeClass;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }
    public static TypeEnum getInstance(Integer code) {
        if (code==0) {
            return BooleanEnum;
        } else if (code==1) {
            return ShortEnum;
        } else if (code==2) {
            return IntegerEnum;
        } else if (code==3) {
            return LongEnum;
        } else if (code==4) {
            return FloatEnum;
        } else if (code==5) {
            return DoubleEnum;
        } else if (code==6) {
            return CharEnum;
        } else if (code==7) {
            return StringEnum;
        } else if (code==8) {
            return ByteEnum;
        }
        return errorEnum;
    }

    public static TypeEnum INSTANCE(Class<?> typeClass) {
        if (Boolean.class.equals(typeClass)||boolean.class.equals(typeClass)) {
            return BooleanEnum;
        } else if (Short.class.equals(typeClass)||short.class.equals(typeClass)) {
            return ShortEnum;
        } else if (Integer.class.equals(typeClass)||int.class.equals(typeClass)) {
            return IntegerEnum;
        } else if (Long.class.equals(typeClass)||long.class.equals(typeClass)) {
            return LongEnum;
        } else if (Float.class.equals(typeClass)||float.class.equals(typeClass)) {
            return FloatEnum;
        } else if (Double.class.equals(typeClass)||double.class.equals(typeClass)) {
            return DoubleEnum;
        } else if (Character.class.equals(typeClass)||char.class.equals(typeClass)) {
            return CharEnum;
        } else if (String.class.equals(typeClass)) {
            return StringEnum;
        } else if (Byte.class.equals(typeClass)||byte.class.equals(typeClass)) {
            return ByteEnum;
        }
        return errorEnum;
    }
}
