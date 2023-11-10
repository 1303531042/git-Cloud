package com.ruoyi.common.influxdb;

import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/10/31
 * @description:
 */
@Data
public class FluxQueryContainer <T extends Measudirection>{
   private Map<T, List<TsKvEntry>> resultMap;

   public Map<T, List<TsKvEntry>> getSimilarMeasudirectionEntries(T m) {
      return resultMap.keySet().stream()
              .filter(key -> key.isSimilar(m))
              .collect(Collectors.toMap(key -> key, key -> resultMap.get(key)));
   }
}
