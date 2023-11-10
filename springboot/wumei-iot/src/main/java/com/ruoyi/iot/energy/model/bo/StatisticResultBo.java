package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther: KUN
 * @date: 2023/9/12
 * @description:
 */
@Data
public class StatisticResultBo {
    /**
     * 统计 0-同比 1-环比
     */
    private Integer statisticType;
    /**
     * 查询颗粒度 0-日 1-月 2-年
     */
    private Integer timeParticles;
    /**
     * 起始时间
     */
    private Date startTime;

    private Map<Long, BigDecimal> queryValueMap;

}
