package com.ruoyi.common.exception.influx;

import com.ruoyi.common.exception.base.BaseException;

/**
 * @auther: KUN
 * @date: 2023/7/10
 * @description: influxdb plus 异常类
 */
public class InfluxException extends BaseException {
    private static final long serialVersionUID = 1L;
    public InfluxException( String defaultMessage)
    {
        super("influx", null, null, defaultMessage);
    }


}
