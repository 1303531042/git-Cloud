package com.ruoyi.simulation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/4/20
 * @description: product 与 simulation 关系类
 */
@Data
@TableName("product_simulation")
public class ProductSimulation {
    private int productId;
    private String simulationName;
}
