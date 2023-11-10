package com.ruoyi.iot.energy.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatisplus.BaseMapperPlus;
import com.ruoyi.iot.energy.domain.AreaDevice;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.influx.AreaDeviceInfluxBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 设备所属区域数据层
 */
public interface AreaDeviceMapper extends BaseMapperPlus<AreaDeviceMapper, AreaDevice, AreaDevice> {

    IPage<AreaDeviceInfoBo> selectAreaDeviceInfoList(IPage<AreaDeviceInfoBo> page, @Param(Constants.WRAPPER) Wrapper<DeviceDashboardBo> queryWrapper);

    Integer selectAreaDeviceOnlineTotal(@Param("areaIds") List<Integer> areaIds);

    Integer selectAreaDeviceOfflineTotal(@Param("areaIds")List<Integer> areaIds);

    IPage<AreaDeviceDetailedInfoBo> selectAreaDeviceDetailedInfoList(IPage<AreaDeviceDetailedInfoBo> page, @Param(Constants.WRAPPER) Wrapper<AreaDeviceListBo> wrapper);

    List<AreaDeviceInfluxBo> selectAreaDeviceInfluxBoList(AreaDeviceInfluxBo bo);

    List<AreaDeviceAndEnergyAndAreaBo> selectAreaDeviceAndEnergyAndAreaBoList(AreaEnergyBo areaEnergyBo);
}
