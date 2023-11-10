package com.ruoyi.iot.model.ThingsModelItem;

import lombok.Data;

@Data
public class ThingsModelItemBase
{
    /** 物模型唯一标识符 */
    private String id;
    /** 物模型名称 */
    private String name;
    /** 物模型值 */
    private String value;
    /** 是否首页显示（0-否，1-是） */
    private Integer isTop;
    /** 是否实时监测（0-否，1-是） */
    private Integer isMonitor;
    /** 数据类型 */
    private String type;
    /** 影子值 */
    private String shadow;

}
