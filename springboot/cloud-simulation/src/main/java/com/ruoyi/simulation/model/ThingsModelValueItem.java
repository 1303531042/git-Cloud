package com.ruoyi.simulation.model;

import lombok.Data;

/**
 * 物模型值的项
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ThingsModelValueItem
{
    /** 物模型唯一标识符 */
    private String id;

    /** 物模型值 */
    private String value;

    /** 影子值 **/
    private String shadow;


}
