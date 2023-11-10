package com.ruoyi.simulation.automessage;

import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/8/18
 * @description: 保存该表达式实体对象的所属类
 */
@Data
public class AutoRequestExpressionWrapper {
    /**
     * 该表达式实体对象所属类地址
     */
    private String AutoRequestExpressionClassPath;
    /**
     * 表达式对象
     */
    private String autoRequestExpressionJSON;

}
