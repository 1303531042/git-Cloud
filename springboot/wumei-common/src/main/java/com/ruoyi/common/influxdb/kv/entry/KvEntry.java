/**
 * Copyright © 2016-2023 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ruoyi.common.influxdb.kv.entry;

import com.ruoyi.common.influxdb.kv.DataType;

import java.io.Serializable;
import java.util.Optional;

/**
 * 用于获取键和值的接口
 */
public interface KvEntry extends Serializable {
    /**
     * 获取该entry的key名
     */
    String getKey();

    /**
     *  获取该entry的value的类型
     */
    DataType getDataType();

    /**
     *  获取value 的 string类型
     */
    Optional<String> getStrValue();

    /**
     *  获取value 的 long类型
     */
    Optional<Long> getLongValue();

    /**
     *  获取value 的 boolean类型
     */
    Optional<Boolean> getBooleanValue();

    /**
     *  获取value 的 Double类型
     */
    Optional<Double> getDoubleValue();

    /**
     *  获取value 的 json类型
     */
    Optional<String> getJsonValue();

    /**
     *  获取value 将其转为string类型
     */
    String getValueAsString();

    /**
     *  获取value 的 object类型
     */
    Object getValue();
}
