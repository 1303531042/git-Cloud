package com.ruoyi.web.eneity.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class StatesDTO implements Serializable {
    /**
     * 物美中设备的id
     */
    private String deviceId;
    /**
     * 属性id
     */
    private String entityId;
    /**
     * 设备名
     */
    private String name;
    /**
     * 需要的具体值
     */
    private String state;
    /**
     * 写入时间
     */
    private Timestamp timestamp;
    /**
     * 来源
     */
    private String source;

    /**
     * 标识符
     */
    private String identity;
}
