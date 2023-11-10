package com.ruoyi.simulation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.simulation.domain.SimulationDevice;
import com.ruoyi.simulation.domain.vo.SimulationDeviceVo;
import com.ruoyi.simulation.manager.SimulationDeviceManager;
import com.ruoyi.simulation.mapper.SimulationDeviceMapper;
import com.ruoyi.simulation.service.SimulationDeviceService;
import com.ruoyi.simulation.service.SimulationService;
import com.ruoyi.simulation.utils.OptionChannelUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description: 模拟设备服务层实现类
 */
@Service
public class SimulationDeviceServiceImpl implements SimulationDeviceService {
    @Autowired
    private SimulationDeviceMapper simulationDeviceMapper;

    @Autowired
    private SimulationService simulationService;

    public SimulationDeviceManager getSimulationDeviceManager() {
        return SpringUtils.getBean(SimulationDeviceManager.class);
    }

    @Override
    public SimulationDevice createSimulationDevice(SimulationDeviceVo simulationDeviceVo) {

        Integer productId = simulationDeviceVo.getProductId();
        String deviceCode = simulationDeviceVo.getDeviceCode();
        String deviceName = simulationDeviceVo.getDeviceName();
        String channelCode = simulationDeviceVo.getChannelCode();

        Optional<Channel> channelOp = simulationService.getChannel(simulationDeviceVo.getSimulationName(), simulationDeviceVo.getChannelCode());
        //设置设备基本信息
        SimulationDevice simulationDevice = new SimulationDevice();
        simulationDevice.setProductId(productId);
        simulationDevice.setDeviceCode(deviceCode);
        simulationDevice.setDeviceName(deviceName);
        simulationDevice.setChannelCode(channelCode);
        simulationDevice.setThingsModel(simulationDeviceVo.getThingsModel());

        //设置通过channel 状态 设置设备初始状态
        Integer status = OptionChannelUtils.checkChannelStatus(channelOp);
        simulationDevice.setStatus(status);

        //通过channel 获取 ip 设置
        InetSocketAddress socketAddress = (InetSocketAddress) channelOp.map(Channel::remoteAddress).get();
        simulationDevice.setNetworkIp(socketAddress.getAddress().getHostAddress());

        //设置创建时间和更新时间为当前时间
        Date date = new Date();
        simulationDevice.setCreateTime(date);
        simulationDevice.setUpdateTime(date);

        //存入数据库
        simulationDeviceMapper.insert(simulationDevice);
        //交给manager管理
        getSimulationDeviceManager().attach(simulationDevice);
        return simulationDevice;
    }

    @Override
    public List<SimulationDevice> queryAll() {
        return simulationDeviceMapper.selectList(new QueryWrapper<SimulationDevice>());
    }
}
