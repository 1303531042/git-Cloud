package com.ruoyi.iot.energy.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 区域表
 */
@Data
@TableName("iot_area")
public class Area {
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
     * 是否隐藏
     */
    private Integer visible;
    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 创建时间
     */
    private Date createTime;
}
