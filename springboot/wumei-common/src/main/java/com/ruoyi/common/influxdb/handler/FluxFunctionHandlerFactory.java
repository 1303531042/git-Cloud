package com.ruoyi.common.influxdb.handler;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.FluxFunction;
import java.util.HashMap;
import java.util.Map;


/**
 * flux函数处理工厂
 * @param <T>
 */
public class FluxFunctionHandlerFactory<T extends Measudirection> {

    private final Map<FluxFunction, FluxFunctionHandler.FunctionHandler<T>> fluxFunctionHandlerMap = new HashMap<>();
    public FluxFunctionHandler.FunctionHandler<T> createHandler(FluxFunction fluxFunction) {
        switch (fluxFunction) {
            case NULL_FUNCTION:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.NullFunctionHandler<T>());
            case LAST_FUNCTION:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.LastFunctionHandler<T>());
            case FIRST_FUNCTION:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.firstFunctionHandler<T>());
            case CUMULATIVE_SUM_FUNCTION:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.cumulativeSumFunctionHandler<T>());
            case INCREASE_FUNCTION:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.increaseFunctionHandler<T>());
            case MEAN_FUNCTION:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.meanFunctionHandler<T>());
            case DIFFERENCE:
                return fluxFunctionHandlerMap.computeIfAbsent(fluxFunction, key -> new FluxFunctionHandler.differenceFunctionHandler<T>());
            default:
                return new FluxFunctionHandler.NullFunctionHandler<T>();
        }

    }
    }