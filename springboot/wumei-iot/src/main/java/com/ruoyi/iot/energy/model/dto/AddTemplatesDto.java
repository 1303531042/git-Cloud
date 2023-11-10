package com.ruoyi.iot.energy.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther: KUN
 * @date: 2023/10/16
 * @description:
 */
@Data
public class AddTemplatesDto {
    /**
     * 区域id
     */
    private Integer areaId;

    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 文件
     */
    private MultipartFile file;
}
