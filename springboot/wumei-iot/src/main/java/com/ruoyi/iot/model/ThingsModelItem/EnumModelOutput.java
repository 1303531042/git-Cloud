package com.ruoyi.iot.model.ThingsModelItem;

import lombok.Data;

import java.util.List;
@Data
public class EnumModelOutput extends ThingsModelItemBase
{
    private List<EnumItemOutput> enumList;

}
