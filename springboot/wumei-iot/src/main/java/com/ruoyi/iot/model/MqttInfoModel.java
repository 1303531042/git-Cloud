package com.ruoyi.iot.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * id和name
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class MqttInfoModel
{
    private String clientid;

    private String username;

    private String password;


}
