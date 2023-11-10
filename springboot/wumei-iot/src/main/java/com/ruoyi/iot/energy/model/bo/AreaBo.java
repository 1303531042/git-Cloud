package com.ruoyi.iot.energy.model.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description:
 */
@Data
public class AreaBo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 该区域归属租户id
     */
    private Integer tenantId;
    /**
     * 区域名
     */
    private String areaName;
    /**
     * 父区域id
     */
    private Integer parentId;
    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 子区域
     */
    private List<AreaBo> children = new ArrayList<>();
}
