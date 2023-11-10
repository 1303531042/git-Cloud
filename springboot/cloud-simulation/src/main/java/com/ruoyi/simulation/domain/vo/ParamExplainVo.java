package com.ruoyi.simulation.domain.vo;

import com.ruoyi.simulation.enums.TypeEnum;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/5/29
 * @description: 协议参数说明
 */
@Data
public class ParamExplainVo {
    /**
     *
     * @return 解释参数 提供前端展示
     */
    String explain;

    /**
     * 前端展示参数id
     * @return
     */
    int id;

    /**
     * 参数类型
     */
    TypeEnum classType;
}
