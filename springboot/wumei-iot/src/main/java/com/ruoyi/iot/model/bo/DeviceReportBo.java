package com.ruoyi.iot.model.bo;

import com.ruoyi.iot.model.ThingsModels.ThingsModelValuesInput;
import lombok.Data;

/**
 * @auther: KUN
 * @date: 2023/9/14
 * @description:
 */
@Data
public class DeviceReportBo {
    private ThingsModelValuesInput input;
    private int type;
    private boolean isShadow;

}
