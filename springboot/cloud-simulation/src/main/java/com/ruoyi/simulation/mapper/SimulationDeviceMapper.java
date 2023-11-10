package com.ruoyi.simulation.mapper;

import com.ruoyi.common.mybatisplus.BaseMapperPlus;
import com.ruoyi.simulation.domain.SimulationDevice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description: 模拟设备表数据层
 */
@Mapper
public interface SimulationDeviceMapper extends BaseMapperPlus<SimulationDeviceMapper, SimulationDevice, SimulationDevice> {

}
