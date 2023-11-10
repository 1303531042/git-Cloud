package com.ruoyi.iot.energy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.mybatisplus.page.PageQuery;
import com.ruoyi.iot.energy.domain.AreaDevice;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.influx.AreaDeviceInfluxBo;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 设备所属区域服务层
 */
public interface AreaDeviceService {
    List<AreaDevice> queryListByAreasAndProductIds(List<Integer> productIds, List<Integer> areaList);

    List<AreaDevice> queryListByAreasAndProductId(Integer productId, List<Integer> areaList);

    /**
     * 查看区域设备信息
     * @param deviceDashboardBo
     * @return
     */
    IPage<AreaDeviceInfoBo> queryAreaDeviceInfos(DeviceDashboardBo deviceDashboardBo, PageQuery pageQuery);

    AreaDeviceStatusBo queryAreaDeviceStatus( List<Integer> areaIds);

    IPage<AreaDeviceDetailedInfoBo> queryAreaDeviceDetailedInfoList(AreaDeviceListBo bo, PageQuery pageQuery);

    /**
     * 添加一个areaDevice
     * @param areaDevice
     * @return
     */
    int saveAreaDevice(AreaDevice areaDevice);

    /**
     * 修改设备所在区域
     * @param areaDevice
     * @return
     */
    int updateAreaDevice(AreaDevice areaDevice);

    int deleteByDeviceIds(List<Long> deviceIds);

    void saveAreaDevices(List<AreaDevice> areaDeviceList);

    List<AreaDeviceInfluxBo> queryAreaDeviceInfluxBoList(AreaDeviceInfluxBo bo);

    List<AreaDeviceAndEnergyAndAreaBo> queryAreaDeviceAndEnergyAndAreaBoList(AreaEnergyBo areaEnergyBo);
}
