package com.ruoyi.iot.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 导入产品物模型的输入对象
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class ImportThingsModelInput
{
    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String ProductName;

    /** 通用物模型ID集合 */
    private Long[] templateIds;


}
