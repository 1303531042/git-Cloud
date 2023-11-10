package com.ruoyi.common.influxdb.cache;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.wrapper.query.FluxQueryWrapper;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/10/26
 * @description:
 */
public interface CacheProcessor<T extends Measudirection>{

    List<String> getWrapperSimpleCacheKeys(FluxQueryWrapper<T> wrapper);
    String getMeasudirectionSimpleKey(T measudirection);

}
