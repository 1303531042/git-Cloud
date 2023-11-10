package com.ruoyi.common.influxdb.handler;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.wrapper.query.FluxQueryWrapper;
/**
 * @auther: KUN
 * @date: 2023/8/14
 * @description:
 */
public class FluxFunctionHandler {
    public interface FunctionHandler<T extends Measudirection> {
        FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper);
    }

    public static class NullFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper;
        }
    }

    public static class LastFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper.last();
        }
    }
    public static class cumulativeSumFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper.cumulativeSum();
        }
    }

    public static class increaseFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper.increase();
        }
    }
    public static class firstFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper.first();
        }
    }
    public static class meanFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper.mean();
        }
    }
    public static class differenceFunctionHandler<T extends Measudirection> implements FunctionHandler<T> {
        @Override
        public FluxQueryWrapper<T> handle(FluxQueryWrapper<T> queryWrapper) {
            return queryWrapper.mean();
        }
    }


}
