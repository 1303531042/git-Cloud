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
public class ThingsModelShadow
{
    public ThingsModelShadow(){
        this.properties=new ArrayList<>();
        this.functions=new ArrayList<>();
    }

    public ThingsModelShadow(List<IdentityAndName> properties, List<IdentityAndName> functions){
        this.properties=properties;
        this.functions=functions;
    }

    /** 属性 */
    List<IdentityAndName> properties;

    /** 功能 */
    List<IdentityAndName> functions;


}
