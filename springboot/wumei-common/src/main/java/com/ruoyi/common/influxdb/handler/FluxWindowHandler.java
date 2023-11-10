package com.ruoyi.common.influxdb.handler;


import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.cache.container.Interval;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.wrapper.IntervalWrapper;
import com.ruoyi.common.influxdb.wrapper.query.FluxQueryWrapper;

/**
 * @auther: KUN
 * @date: 2023/8/14
 * @description:
 */
public class FluxWindowHandler{

    public interface WindowFunctionHandler<T extends Measudirection > {
        IntervalWrapper handle(WindowTimeIntervalUnit windowTimeIntervalUnit, Integer time,WindowFunction windowFunction, FluxQueryWrapper<T> queryWrapper);
    }

    public static class HourWindowFunctionHandler<T extends Measudirection> implements WindowFunctionHandler<T> {
        @Override
        public IntervalWrapper handle(WindowTimeIntervalUnit windowTimeIntervalUnit, Integer time,WindowFunction windowFunction, FluxQueryWrapper<T> queryWrapper) {
            queryWrapper.measurement(queryWrapper.getMeasurement() + "_hour");
            return new IntervalWrapper(Interval.HOUR_INTERVAL, Interval.HOUR_INTERVAL.getInterval());
        }
    }

    public static class DayWindowFunctionHandler<T extends Measudirection> implements WindowFunctionHandler<T> {
        @Override
        public IntervalWrapper handle(WindowTimeIntervalUnit windowTimeIntervalUnit, Integer time, WindowFunction windowFunction, FluxQueryWrapper<T> queryWrapper) {

            queryWrapper.measurement(queryWrapper.getMeasurement() + "_day");
            return new IntervalWrapper(Interval.DAY_INTERVAL, Interval.DAY_INTERVAL.getInterval());
        }
    }
    public static class FreeWindowFunctionHandler<T extends Measudirection> implements WindowFunctionHandler<T> {
        @Override
        public IntervalWrapper handle(WindowTimeIntervalUnit windowTimeIntervalUnit, Integer time, WindowFunction windowFunction, FluxQueryWrapper<T> queryWrapper) {
            queryWrapper.window(time, windowTimeIntervalUnit, windowFunction);
            return new IntervalWrapper(Interval.FREE_INTERVAL, windowTimeIntervalUnit.getInterval() * time);
        }

    }
}
