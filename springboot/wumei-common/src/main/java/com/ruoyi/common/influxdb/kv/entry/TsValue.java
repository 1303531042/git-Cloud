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

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 时序数据的value存储的实体类
 */
@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TsValue {

    public static final TsValue EMPTY = new TsValue(0, "");
    /**
     * 该时序值的 对应的时间 单位毫秒
     */
    private  long ts;
    /**
     * 该时序值的string类型存储值
     */
    private  String value;
    /**
     * 在此次查询的第几行 一般不用
     */
    private  Long count;

    public TsValue(long ts, String value) {
        this(ts, value, null);
    }

    public TsValue(long ts, String value, Long o) {
        this.ts = ts;
        this.value = value;
        this.count = o;
    }
}
