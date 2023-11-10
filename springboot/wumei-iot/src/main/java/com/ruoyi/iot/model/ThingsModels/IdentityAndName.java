package com.ruoyi.iot.model.ThingsModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物模型值的项
 *
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityAndName {


    /** 物模型唯一标识符 */
    private String id;

    /** 物模型值 */
    private String value;



}
