package com.ruoyi.common.influxdb.enumeration;

/**
 * flux模版枚举类
 */
public enum FluxTemplate {
    /**
     * 设置时区
     */
    ZONE("import \"timezone\"\n" +
            "option location = timezone.fixed(offset: 8h)"),

    /**
     * 查询桶模版
     */
    BUCKET_FLUX("from(bucket: \"#{[bucket]}\")\n"),

    /**
     * 查询时间模版
     */
    RANGE_TIME_FLUX("  |> range(start: #{[startDate]}, stop: #{[endDate]})\n"),
    /**
     * 查询表模版
     */
    MEASUREMENT_FLUX("|> filter(fn: (r) => r[\"_measurement\"] == \"#{[measurement]}\")\n"),


    /**
     * 过滤器模版
     */
    FILTER_FLUX("|> filter(fn: (r) => #{[filters]})\n"),

    /**
     * or过滤器模版
     */
    OR_FILTER_FLUX("or r[\"#{[tag]}\"] == \"#{[tagValue]}\" "),

    /**
     * and过滤器模版
     */
    AND_FILTER_FLUX("and r[\"#{[tag]}\"] == \"#{[tagValue]}\" "),

    /**
     * 选择输出列模版
     */
    COLUMN_FLUX("|> filter(fn: (r) => r[\"_field\"] == \"#{[columnName]}\")\n"),



    /**
     * 分区函数模版
     */
    WINDOW_FLUX("|> aggregateWindow(every: #{[timeValue]}#{[timeUnit]}, #{[windowFunction]})\n"),



    /**
     * 集成以下函数
     */
    FUNCTION_FLUX(""),

    /**
     * 累计函数模版
     */
    CUMULATIVE_SUM_FLUX("|> cumulativeSum()\n"),

    /**
     * 非负差异的累计和模版
     */
    INCREASE_FLUX("|>increase()\n"),

    /**
     * 非空第一个值模版
     */
    FIRST_FLUX("|>first()\n"),

    /**
     * 非空最后一个值模版
     */
    LAST_FLUX("|>last()\n"),

    /**
     * 平均数模版
     */
    MEAN_FLUX("|>mean()\n"),

    /**
     * 减去上一值
     */
    DIFFERENCE("|>difference()\n"),
    /**
     * 列变行函数模版
     */
    PIVOT_FLUX("> pivot(rowKey: [\"_time\"], columnKey: [\"#{[columnValue]}\"], valueColumn: \"_value\")\n"),

    /**
     * map函数模版
     */
    MAP_FLUX("|> map(fn: (r) => ({r with _value: #{[expression]} }))\n");
    private String value;

    FluxTemplate(String value) {
        this.value = value;

    }

    public String getValue() {
        return this.value;
    }
    }
