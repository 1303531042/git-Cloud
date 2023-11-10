package com.ruoyi.common.influxdb.kv.entry;

import com.ruoyi.common.influxdb.kv.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/8/3
 * @description:
 */
public class StringDataEntry extends BasicKvEntry{
    private static final long serialVersionUID = 1L;

    private String value;

    public StringDataEntry() {

    }

    public StringDataEntry(String key, String value) {
        super(key);
        this.value = value;
    }
    /**
     * 获取该entry的value的类型
     */
    @Override
    public DataType getDataType() {
        return DataType.STRING;
    }

    @Override
    public Optional<String> getStrValue() {
        return Optional.ofNullable(value);
    }

    /**
     * 获取value 将其转为string类型
     */
    @Override
    public String getValueAsString() {
        return value;
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
        if (this == o)
            return true;
        if (!(o instanceof StringDataEntry))
            return false;
        if (!super.equals(o))
            return false;
        StringDataEntry that = (StringDataEntry) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "StringDataEntry{" + "value='" + value + '\'' + "} " + super.toString();
    }

}
