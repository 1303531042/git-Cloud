package com.ruoyi.iot.energy.model.bo;

import com.ruoyi.iot.energy.domain.AreaDevice;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @auther: KUN
 * @date: 2023/9/12
 * @description:
 */
@Data
public class DeviceStatisticIncreaseLastBo {
    private String identity;
    private String deviceId;
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
