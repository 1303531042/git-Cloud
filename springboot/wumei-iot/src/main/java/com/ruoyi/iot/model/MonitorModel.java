package com.ruoyi.iot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 动作
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public class MonitorModel
{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String value;


}
