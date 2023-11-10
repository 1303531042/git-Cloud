package com.ruoyi.common.influxdb.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/8/3
 * @description: influx 查询时间格式化
 */
public class InfluxTimeFormate {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static String getFormate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Shanghai"));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Shanghai"));
        String formattedDate = DATE_FORMAT.format(zonedDateTime);
        String formattedTime = TIME_FORMAT.format(zonedDateTime);
        return formattedDate + "T" + formattedTime + "+08:00";
    }
}
