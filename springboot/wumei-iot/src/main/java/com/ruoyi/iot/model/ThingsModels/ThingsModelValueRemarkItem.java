package com.ruoyi.iot.model.ThingsModels;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 物模型值的项
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ThingsModelValueRemarkItem
{
    /** 物模型唯一标识符 */
    private String id;

    /** 物模型值 */
    private String value;

    /** 备注 **/
    private String remark;



}
