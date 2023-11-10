package com.ruoyi.iot.energy.model.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/10/30
 * @description:
 */
@Data
public class ProductStatisticIncreaseLastBo {
    /**
     * 能源区域
     */
    private Integer areaId;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 能源属性描述符
     */
    private String identity;
    /**
     * 查询颗粒度 0-日 1-月 2-年
     */
    private Integer timeParticles;

    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
}
