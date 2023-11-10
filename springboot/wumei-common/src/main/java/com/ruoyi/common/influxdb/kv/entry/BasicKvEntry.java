package com.ruoyi.common.influxdb.kv.entry;

import java.util.Objects;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/8/2
 * @description: KvEntry基础实现抽象类
 */
public abstract class BasicKvEntry implements KvEntry{

    private String key;

    protected BasicKvEntry() {

    }

    protected BasicKvEntry(String key) {
        this.key = key;
    }




    /**
     * 获取该entry的key名
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * 获取value 的 string类型
     */
    @Override
    public Optional<String> getStrValue() {
        return Optional.empty();
    }

    /**
     * 获取value 的 long类型
     */
    @Override
    public Optional<Long> getLongValue() {
        return Optional.empty();
    }

    /**
     * 获取value 的 boolean类型
     */
    @Override
    public Optional<Boolean> getBooleanValue() {
        return Optional.empty();
    }

    /**
     * 获取value 的 Double类型
     */
    @Override
    public Optional<Double> getDoubleValue() {
        return Optional.empty();
    }

    /**
     * 获取value 的 json类型
     */
    @Override
    public Optional<String> getJsonValue() {
        return Optional.empty();
    }

    /**
     * key相同时 这两个对象相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicKvEntry)) return false;
        BasicKvEntry that = (BasicKvEntry) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "BasicKvEntry{" +
                "key='" + key + '\'' +
                '}';
    }

}
