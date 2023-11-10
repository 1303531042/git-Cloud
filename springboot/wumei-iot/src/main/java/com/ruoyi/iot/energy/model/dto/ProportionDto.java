package com.ruoyi.iot.energy.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther: KUN
 * @date: 2023/9/12
 * @description:
 */
@Data
public class ProportionDto {
    /**
     * 区域id
     */
    private Integer areaId;


    /**
     * 查询颗粒度 0-日 1-月 2-年
     */
    private Integer timeParticles;

    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;



    /**
     * 能源类型 0-用电 1-用水 2-发电
     */
    private int energyType;

}
