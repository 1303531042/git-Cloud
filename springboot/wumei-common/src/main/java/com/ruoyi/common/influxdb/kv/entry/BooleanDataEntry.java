package com.ruoyi.common.influxdb.kv.entry;

import com.ruoyi.common.influxdb.kv.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/8/2
 * @description: 值为boolean类型的Entry
 */
public class BooleanDataEntry extends  BasicKvEntry {
    /**
     * 存储值
     */
    private Boolean value;

    public BooleanDataEntry(String key, Boolean value) {
        super(key);
        this.value = value;
    }

    public BooleanDataEntry() {

    }

    /**
     * 获取该entry的value的类型
     */
    @Override
    public DataType getDataType() {
        return DataType.BOOLEAN;
    }

    @Override
    public Optional<Boolean> getBooleanValue() {
        return Optional.ofNullable(value);
    }

    /**
     * 获取value 将其转为string类型
     */
    @Override
    public String getValueAsString() {
        return Boolean.toString(value);
    }

    /**
     * 获取value 的 object类型
     */
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanDataEntry)) return false;
        if (!super.equals(o)) return false;
        BooleanDataEntry that = (BooleanDataEntry) o;
        return Objects.equals(value, that.value);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "BooleanDataEntry{" +
                "value=" + value +
                "} " + super.toString();
    }
}
