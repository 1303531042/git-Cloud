package com.ruoyi.common.influxdb.kv.entry;

import com.ruoyi.common.influxdb.kv.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/8/3
 * @description: 值为long类型的Entry
 */
public class LongDataEntry extends BasicKvEntry{
    private Long value;

    public LongDataEntry() {

    }

    public LongDataEntry(String key, Long value) {
        super(key);
        this.value = value;
    }
    /**
     * 获取该entry的value的类型
     */
    @Override
    public DataType getDataType() {
        return DataType.LONG;
    }

    @Override
    public Optional<Long> getLongValue() {
        return Optional.ofNullable(value);
    }

    /**
     * 获取value 将其转为string类型
     */
    @Override
    public String getValueAsString() {
        return Long.toString(value);
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
        if (!(o instanceof LongDataEntry)) return false;
        if (!super.equals(o)) return false;
        LongDataEntry that = (LongDataEntry) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "LongDataEntry{" +
                "value=" + value +
                "} " + super.toString();
    }
}
