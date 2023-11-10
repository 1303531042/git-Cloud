package com.ruoyi.iot.model.ThingsModels;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * 产品分类的Id和名称输出
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class FunctionDto
{
    /** 物模型唯一标识符 */
    private String id;
    /** 物模型名称 */
    private String name;
    /** 是否首页显示（0-否，1-是） */
    private Integer isTop;
    /** 是否实时监测（0-否，1-是） */
    @Excel(name = "是否实时监测", readConverterExp = "0=-否，1-是")
    private Integer isMonitor;
    /** 数据定义 */
    private JSONObject datatype;


}
