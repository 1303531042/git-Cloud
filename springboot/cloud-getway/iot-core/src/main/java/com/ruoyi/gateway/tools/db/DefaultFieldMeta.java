package com.ruoyi.gateway.tools.db;

public class DefaultFieldMeta implements FieldMeta {

    /**
     * 字段类型
     */
    private int type;

    /**
     * 字段名称
     */
    private String name;

    public DefaultFieldMeta(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
