package com.ruoyi.iot.model.ThingsModels;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品分类的Id和名称输出
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ThingsModelsDto
{
    public ThingsModelsDto(){
        properties=new ArrayList<>();
        functions=new ArrayList<>();
        events=new ArrayList<>();
    }

    /** 属性 */
    private List<PropertyDto> properties;
    /** 功能 */
    private List<FunctionDto> functions;
    /** 事件 */
    private List<EventDto> events;


}
