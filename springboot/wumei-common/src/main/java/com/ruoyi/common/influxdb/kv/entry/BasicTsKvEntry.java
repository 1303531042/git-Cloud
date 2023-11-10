package com.ruoyi.common.influxdb.kv.entry;

import com.ruoyi.common.influxdb.kv.DataType;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/8/3
 * @description:
 */
public class BasicTsKvEntry implements TsKvEntry{
    private static final int MAX_CHARS_PER_DATA_POINT = 512;
    protected long ts;
    @Valid
    private KvEntry kv;

    public BasicTsKvEntry(long ts, KvEntry kv) {
        this.ts = ts;
        this.kv = kv;
    }

    public BasicTsKvEntry() {

    }

    /**
     * 该entry的 对应的时间 单位毫秒
     */
    @Override
    public long getTs() {
        return ts;
    }

    @Override
    public int getDataPoints() {
        int length;
        switch (getDataType()) {
            case STRING:
                length = getStrValue().get().length();
                break;
            default:
                return 1;
        }
        return Math.max(1, (length + MAX_CHARS_PER_DATA_POINT - 1) / MAX_CHARS_PER_DATA_POINT);
    }


    /**
     * 获取该entry的key名
     */
    @Override
    public String getKey() {
        return kv.getKey();
    }

    /**
     * 获取该entry的value的类型
     */
    @Override
    public DataType getDataType() {
        return kv.getDataType();
    }

    /**
     * 获取value 的 string类型
     */
    @Override
    public Optional<String> getStrValue() {
        return kv.getStrValue();
    }

    /**
     * 获取value 的 long类型
     */
    @Override
    public Optional<Long> getLongValue() {
        return kv.getLongValue();
    }

    /**
     * 获取value 的 boolean类型
     */
    @Override
    public Optional<Boolean> getBooleanValue() {
        return kv.getBooleanValue();
    }

    /**
     * 获取value 的 Double类型
     */
    @Override
    public Optional<Double> getDoubleValue() {
        return kv.getDoubleValue();
    }

    /**
     * 获取value 的 json类型
     */
    @Override
    public Optional<String> getJsonValue() {
        return kv.getJsonValue();
    }

    /**
     * 获取value 将其转为string类型
     */
    @Override
    public String getValueAsString() {
        return kv.getValueAsString();
    }

    /**
     * 获取value 的 object类型
     */
    @Override
    public Object getValue() {
        return kv.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicTsKvEntry)) return false;
        BasicTsKvEntry that = (BasicTsKvEntry) o;
        return getTs() == that.getTs() &&
                Objects.equals(kv, that.kv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTs(), kv);
    }

    @Override
    public String toString() {
        return "BasicTsKvEntry{" +
                "ts=" + ts +
                ", kv=" + kv +
                '}';
    }
}
