package com.ruoyi.common.influxdb.kv.entry;

import com.ruoyi.common.influxdb.kv.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/8/2
 * @description: 值为Double类型的Entry
 */
public class DoubleDataEntry extends BasicKvEntry {
    private  Double value;

    public DoubleDataEntry(String key, Double value) {
        super(key);
        this.value = value;
    }

    public DoubleDataEntry() {

    }

    /**
     * 获取该entry的value的类型
     */
    @Override
    public DataType getDataType() {
        return DataType.DOUBLE;
    }

    @Override
    public Optional<Double> getDoubleValue() {
        return Optional.ofNullable(value);
    }

    /**
     * 获取value 将其转为string类型
     */
    @Override
    public String getValueAsString() {
        return Double.toString(value);
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
        if (!(o instanceof DoubleDataEntry)) return false;
        if (!super.equals(o)) return false;
        DoubleDataEntry that = (DoubleDataEntry) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "DoubleDataEntry{" +
                "value=" + value +
                "} " + super.toString();

    }
}
