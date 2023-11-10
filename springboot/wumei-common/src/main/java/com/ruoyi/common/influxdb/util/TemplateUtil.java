package com.ruoyi.common.influxdb.util;

/**
 * @auther: KUN
 * @date: 2023/7/14
 * @description: influx 模版工具类
 */
public class TemplateUtil {
    public static StringBuilder replaceTemplate(StringBuilder template, String key, String value) {

        int start = template.indexOf("#{[" + key + "]}");
        if (start != -1) {
            int end = start + key.length() + 5;
            template.replace(start, end, value);
        }
        return template;
    }
}
