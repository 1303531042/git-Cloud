package com.ruoyi.iot.energy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatisplus.page.PageQuery;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.iot.domain.Device;
import com.ruoyi.iot.energy.domain.AreaDevice;
import com.ruoyi.iot.energy.mapper.AreaDeviceMapper;
import com.ruoyi.iot.energy.model.bo.*;
import com.ruoyi.iot.energy.service.AreaDeviceService;
import com.ruoyi.iot.influx.AreaDeviceInfluxBo;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 区域设备服务层实现类
 */
@Service
@RequiredArgsConstructor
public class AreaDeviceServiceImpl implements AreaDeviceService {
    private final AreaDeviceMapper areaDeviceMapper;

    @Override
    public List<AreaDevice> queryListByAreasAndProductIds(List<Integer> productIds, List<Integer> areaList) {
        return areaDeviceMapper.selectVoList(new QueryWrapper<AreaDevice>()
                .in("product_id", productIds)
                .in("area_id", areaList)
        );
    }

    @Override
    public List<AreaDevice> queryListByAreasAndProductId(Integer productId, List<Integer> areaList) {
        return areaDeviceMapper.selectVoList(new QueryWrapper<AreaDevice>()
                .eq("product_id", productId)
                .in("area_id", areaList)
        );
    }

    /**
     * 查看区域设备信息
     *
     * @param deviceDashboardBo
     * @return
     */
    @Override
    public IPage<AreaDeviceInfoBo> queryAreaDeviceInfos(DeviceDashboardBo deviceDashboardBo, PageQuery pageQuery) {
        Wrapper<DeviceDashboardBo> queryWrapper = buildDeviceDashboardQueryWrapper(deviceDashboardBo);
        Page<AreaDeviceInfoBo> page = pageQuery.build();
        return areaDeviceMapper.selectAreaDeviceInfoList(page, queryWrapper);

    }

    @Override
    public AreaDeviceStatusBo queryAreaDeviceStatus(List<Integer> areaIds) {
        Integer onlineTotal = areaDeviceMapper.selectAreaDeviceOnlineTotal(areaIds);
        Integer OfflineTotal = areaDeviceMapper.selectAreaDeviceOfflineTotal(areaIds);
        AreaDeviceStatusBo bo = new AreaDeviceStatusBo();
        bo.setOfflineTotal(OfflineTotal);
        bo.setOnlineTotal(onlineTotal);
        return bo;
    }

    /**
     * 查看区域设备详情列表
     *
     * @param bo
     * @param pageQuery
     * @return
     */
    @Override
    public IPage<AreaDeviceDetailedInfoBo> queryAreaDeviceDetailedInfoList(AreaDeviceListBo bo, PageQuery pageQuery) {
        Page<AreaDeviceDetailedInfoBo> page = pageQuery.build();
        Wrapper<AreaDeviceListBo> wrapper = buildAreaDeviceDetailedInfoQueryWrapper(bo);
        return areaDeviceMapper.selectAreaDeviceDetailedInfoList(page, wrapper);
    }

    /**
     * 添加一个areaDevice
     *
     * @param areaDevice
     * @return
     */
    @Override
    public int saveAreaDevice(AreaDevice areaDevice) {
        return areaDeviceMapper.insert(areaDevice);
    }

    /**
     * 修改设备所在区域
     *
     * @param areaDevice
     * @return
     */
    @Override
    public int updateAreaDevice(AreaDevice areaDevice) {
        return areaDeviceMapper.update(areaDevice, new UpdateWrapper<AreaDevice>().eq("device_id", areaDevice.getDeviceId()));

    }

    @Override
    public int deleteByDeviceIds(List<Long> deviceIds) {
        return areaDeviceMapper.delete(new QueryWrapper<AreaDevice>().in("device_id", deviceIds));
    }

    @Override
    public void saveAreaDevices(List<AreaDevice> areaDeviceList) {
        areaDeviceMapper.insertBatch(areaDeviceList);
    }

    @Override
    public List<AreaDeviceInfluxBo> queryAreaDeviceInfluxBoList(AreaDeviceInfluxBo bo) {
        return areaDeviceMapper.selectAreaDeviceInfluxBoList(bo);
    }

    @Override
    public List<AreaDeviceAndEnergyAndAreaBo> queryAreaDeviceAndEnergyAndAreaBoList(AreaEnergyBo areaEnergyBo) {
        return areaDeviceMapper.selectAreaDeviceAndEnergyAndAreaBoList(areaEnergyBo);
    }

    /**
     * 创建AreaDeviceDetailedInfo wrapper
     * @param deviceListBo
     * @return
     */
    private Wrapper<AreaDeviceListBo> buildAreaDeviceDetailedInfoQueryWrapper(AreaDeviceListBo deviceListBo) {
        QueryWrapper<AreaDeviceListBo> wrapper = new QueryWrapper<>();
        if (deviceListBo.getStatus() != null) {
            wrapper.eq("c.status", deviceListBo.getStatus());
        }
        if (deviceListBo.getProductId() != null) {
            wrapper.eq("c.product_id", deviceListBo.getProductId());
        }
        if (deviceListBo.getSerialNumber() != null) {
            wrapper.eq("c.serial_number", deviceListBo.getSerialNumber());
        }
        if (deviceListBo.getAreaIds() != null && !deviceListBo.getAreaIds().isEmpty()) {
            wrapper.in("a.area_id", deviceListBo.getAreaIds());
        }
        if (deviceListBo.getDeviceName() != null) {
            wrapper.eq("c.device_name", deviceListBo.getDeviceName());
        }
        return wrapper;
    }


    /**
     * 创建DeviceDashboard 的wrapper
     * @param deviceDashboardBo
     * @return
     */
    private Wrapper<DeviceDashboardBo> buildDeviceDashboardQueryWrapper(DeviceDashboardBo deviceDashboardBo) {
        QueryWrapper<DeviceDashboardBo> wrapper = new QueryWrapper<>();
        if (deviceDashboardBo.getStatus() != null) {
            wrapper.eq("c.status", deviceDashboardBo.getStatus());
        }
        if (deviceDashboardBo.getProductId() != null) {
            wrapper.eq("c.product_id", deviceDashboardBo.getProductId());
        }
        if (deviceDashboardBo.getSerialNumber() != null) {
            wrapper.eq("c.serial_number", deviceDashboardBo.getSerialNumber());
        }
        if (deviceDashboardBo.getAreaIds() != null && !deviceDashboardBo.getAreaIds().isEmpty()) {
            wrapper.in("a.area_id", deviceDashboardBo.getAreaIds());
        }
        return wrapper;
    }


}
