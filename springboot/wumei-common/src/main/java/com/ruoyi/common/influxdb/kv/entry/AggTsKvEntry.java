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

import lombok.ToString;

@ToString
public class AggTsKvEntry extends BasicTsKvEntry {

    private static final long serialVersionUID = -1933884317450255935L;

    private long count;

    public AggTsKvEntry(long ts, KvEntry kv, long count) {
        super(ts, kv);
        this.count = count;
    }

    public AggTsKvEntry() {

    }

    @Override
    public TsValue toTsValue() {
        return new TsValue(ts, getValueAsString(), count);
    }
}
