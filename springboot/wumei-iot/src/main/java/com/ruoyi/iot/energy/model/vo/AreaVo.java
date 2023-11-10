package com.ruoyi.iot.energy.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description:
 */
@Data
public class AreaVo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 区域名
     */
    private String areaName;


    /**
     * 子区域
     */
    private List<AreaVo> children = new ArrayList<>();
}
