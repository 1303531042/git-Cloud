package com.ruoyi.gateway.tools.db;

import java.util.Map;

public class IotMapEntity {

    /**
     * SpEl支持
     */
    private Object root;

    /**
     * 实体值
     * @see FieldMeta#getName() 作为key
     * @see FieldMeta#getType() value类型
     */
    private Map<String, Object> value;

    public IotMapEntity(Map<String, Object> value) {
        this.value = value;
    }

    public IotMapEntity(Object root, Map<String, Object> value) {
        this.root = root;
        this.value = value;
    }

    public Object getRoot() {
        return root;
    }

    public void setRoot(Object root) {
        this.root = root;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IotMapEntity{" +
                "root=" + root +
                ", value=" + value +
                '}';
    }
}
