package com.ruoyi.common.influxdb.handler;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;

public class WindowFunctionHandlerFactory<T extends Measudirection> {
    private final FluxWindowHandler.WindowFunctionHandler<T> hourWindowHandler = new FluxWindowHandler.HourWindowFunctionHandler<>();
    private final FluxWindowHandler.WindowFunctionHandler<T> dayWindowHandler = new FluxWindowHandler.DayWindowFunctionHandler<>();
    private final FluxWindowHandler.WindowFunctionHandler<T> freeWindowHandler = new FluxWindowHandler.FreeWindowFunctionHandler<>();


    public FluxWindowHandler.WindowFunctionHandler<T> createHandler(WindowTimeIntervalUnit unit, Integer time) {
        if (time == 1) {
            switch (unit) {
                case HOUR:
                    return hourWindowHandler;
                case DAY:
                    return dayWindowHandler;
                default:
                    break;
            }
        }
        return freeWindowHandler;
    }



}