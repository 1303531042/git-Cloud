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
package com.ruoyi.common.influxdb.kv;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public enum DataType {
    STRING(String.class), LONG(Long.class), BOOLEAN(Boolean.class), DOUBLE(Double.class);
    private Class<?> value;

    DataType(Class<?> clazz) {
        this.value = clazz;
    }

    public Class<?> getValue() {
        return value;
    }

    public static DataType getInstance(Class<?> clazz) {
        for (DataType dataType : DataType.values()) {
            if (Objects.equals(dataType.value,clazz)) {
                return dataType;
            }
        }
        throw new RuntimeException("该column类型不允许使用");
    }

}
