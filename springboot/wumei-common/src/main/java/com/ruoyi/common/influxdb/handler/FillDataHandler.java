package com.ruoyi.common.influxdb.handler;

import com.ruoyi.common.influxdb.cache.container.Interval;
import com.ruoyi.common.influxdb.enumeration.FillType;
import com.ruoyi.common.influxdb.kv.entry.BasicTsKvEntry;
import com.ruoyi.common.influxdb.kv.entry.DoubleDataEntry;
import com.ruoyi.common.influxdb.kv.entry.TsKvEntry;
import com.ruoyi.common.influxdb.wrapper.IntervalWrapper;
import java.util.*;

/**
 * @auther: KUN
 * @date: 2023/8/11
 * @description: 补全数据处理类
 */
public class FillDataHandler {
    private static final Map<FillType, FillStrategy> fillStrategies = new HashMap<>();

    static {
        fillStrategies.put(FillType.USE_PREVIOUS, new UsePreviousFillStrategy());
        fillStrategies.put(FillType.DEFAULT_VALUE, new DefaultValueFillStrategy());
        fillStrategies.put(FillType.NULL_VALUE, new NullValueFillStrategy());
        fillStrategies.put(FillType.IGNORE_VALUE, new IgnoreFillStrategy());
    }

    /**
     * 如果查的是原始点数据不进行fill
     * @param intervalWrapper
     * @return
     */
    protected static boolean checktPointerInterval(IntervalWrapper intervalWrapper) {
        if (intervalWrapper.getInterval().equals(Interval.POINTER_INTERVAL)) {
            return true;
        }
        return false;
    }

    /**
     * 策略模式 补全数据接口
     */
    public interface FillStrategy {
        List<TsKvEntry> fill(IntervalWrapper intervalWrapper, List<TsKvEntry> tsKvEntries, Date startTime, Date endTime);
    }

    /**
     * 使用前一个点的数据补全这个点的数据
     */
    private static class UsePreviousFillStrategy implements FillStrategy {
        @Override
        public List<TsKvEntry> fill(IntervalWrapper intervalWrapper, List<TsKvEntry> tsKvEntries, Date startTime, Date endTime) {
            //如果查的是原始点数据不进行fill
            if (checktPointerInterval(intervalWrapper)) {
                return tsKvEntries;
            }
            List<TsKvEntry> filledEntries = new ArrayList<>();

            // 获取起始日期和终止日期的时间戳
            long startTimestamp = startTime.getTime();
            long endTimestamp = endTime.getTime();

            // 计算时间间隔的毫秒数
            long intervalMillis = intervalWrapper.getIntervalTime();

            // 寻找起始日期之前的最后一个数据点
            TsKvEntry lastEntryBeforeStart = new BasicTsKvEntry(1, new DoubleDataEntry(null, null));
            int lastIndex = binarySearch(tsKvEntries, startTimestamp);

            if (lastIndex >= 0) {
                lastEntryBeforeStart = tsKvEntries.get(lastIndex);
            }

            // 一次性生成所有符合时间间隔的时间点
            List<Long> timestamps = new ArrayList<>();
            for (long timestamp = startTimestamp; timestamp <= endTimestamp; timestamp += intervalMillis) {
                timestamps.add(timestamp);
            }

            // 根据时间点填充数据
            for (long timestamp : timestamps) {
                int index = binarySearch(tsKvEntries, timestamp);

                if (index >= 0) {
                    filledEntries.add(tsKvEntries.get(index));
                    lastEntryBeforeStart = tsKvEntries.get(index);
                } else {

                    Object interpolatedValue = lastEntryBeforeStart.getValue();
                    String key = lastEntryBeforeStart.getKey();
                    TsKvEntry interpolatedEntry = new BasicTsKvEntry(timestamp, new DoubleDataEntry(key, (Double) interpolatedValue));
                    filledEntries.add(interpolatedEntry);
                }
            }

            return filledEntries;

        }
        // 二分查找算法
        private int binarySearch(List<TsKvEntry> tsKvEntries, long targetTimestamp) {

            int low = 0;
            int high = tsKvEntries.size() - 1;

            while (low <= high) {
                int mid = (low + high) / 2;
                long midTimestamp = tsKvEntries.get(mid).getTs();

                if (midTimestamp == targetTimestamp) {
                    return mid;
                } else if (midTimestamp < targetTimestamp) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            return high;
        }
    }


    private static class DefaultValueFillStrategy implements FillStrategy {
        @Override
        public List<TsKvEntry> fill(IntervalWrapper intervalWrapper, List<TsKvEntry> tsKvEntries, Date startTime, Date endTime) {
            //如果查的是原始点数据不进行fill
            if (checktPointerInterval(intervalWrapper)) {
                return tsKvEntries;
            }
            List<TsKvEntry> filledEntries = new ArrayList<>();

            // 获取起始日期和终止日期的时间戳
            long startTimestamp = startTime.getTime();
            long endTimestamp = endTime.getTime();

            // 计算时间间隔的毫秒数
            long intervalMillis = intervalWrapper.getIntervalTime();

            // 构建哈希表以进行快速查找
            Map<Long, TsKvEntry> entryMap = new HashMap<>();
            for (TsKvEntry entry : tsKvEntries) {
                entryMap.put(entry.getTs(), entry);
            }

            // 遍历每个对齐的时间点，直到达到或超过终止日期
            for (long timestamp = startTimestamp; timestamp <= endTimestamp; timestamp += intervalMillis) {
                TsKvEntry currentEntry = entryMap.get(timestamp);

                // 如果存在数据点，则将其添加到填充结果中
                if (currentEntry != null) {
                    filledEntries.add(currentEntry);
                } else {
                    // 否则，创建一个默认值的数据点并添加到填充结果中
                    TsKvEntry defaultEntry = new BasicTsKvEntry(timestamp, new DoubleDataEntry(null, 0.0));
                    filledEntries.add(defaultEntry);
                }
            }

            return filledEntries;
        }
    }

    /**
     * 补充空数据
     */
    private static class NullValueFillStrategy implements FillStrategy {
        @Override
        public List<TsKvEntry> fill(IntervalWrapper intervalWrapper, List<TsKvEntry> tsKvEntries, Date startTime, Date endTime) {
            //如果查的是原始点数据不进行fill
            if (checktPointerInterval(intervalWrapper)) {
                return tsKvEntries;
            }
            List<TsKvEntry> filledEntries = new ArrayList<>();

            // 获取起始日期和终止日期的时间戳
            long startTimestamp = startTime.getTime();
            long endTimestamp = endTime.getTime();

            // 计算时间间隔的毫秒数
            long intervalMillis = intervalWrapper.getIntervalTime();

            // 创建哈希表以便快速查找
            Map<Long, TsKvEntry> entryMap = new HashMap<>();
            for (TsKvEntry entry : tsKvEntries) {
                entryMap.put(entry.getTs(), entry);
            }

            // 遍历每个对齐的时间点，直到达到或超过终止日期
            for (long timestamp = startTimestamp; timestamp <= endTimestamp; timestamp += intervalMillis) {
                TsKvEntry currentEntry = entryMap.get(timestamp);

                // 如果当前时间点有数据点，则将其添加到填充结果中
                if (currentEntry != null) {
                    filledEntries.add(currentEntry);
                } else {
                    // 否则添加一个空值的数据点
                    TsKvEntry nullEntry = new BasicTsKvEntry(timestamp, new DoubleDataEntry(null, null));
                    filledEntries.add(nullEntry);
                }
            }

            return filledEntries;
        }
    }

    /**
     * 忽略间隔 直接返回查询值
     */
    private static class IgnoreFillStrategy implements FillStrategy {
        @Override
        public List<TsKvEntry> fill(IntervalWrapper intervalWrapper, List<TsKvEntry> tsKvEntries, Date startTime, Date endTime) {
            return tsKvEntries;
        }
    }

    public static List<TsKvEntry> fillResult(FillType fillType, IntervalWrapper intervalWrapper, List<TsKvEntry> tsKvEntries, Date startTime, Date endTime) {

        FillStrategy fillStrategy = fillStrategies.get(fillType);
        if (fillStrategy != null) {
            return fillStrategy.fill(intervalWrapper, tsKvEntries, startTime, endTime);
        } else {
            return tsKvEntries;
        }
    }



}
