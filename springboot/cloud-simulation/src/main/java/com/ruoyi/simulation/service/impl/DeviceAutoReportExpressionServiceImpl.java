package com.ruoyi.simulation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.simulation.domain.DeviceAutoReportExpression;
import com.ruoyi.simulation.mapper.DeviceAutoReportExpressionMapper;
import com.ruoyi.simulation.service.DeviceAutoReportExpressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @auther: KUN
 * @date: 2023/8/18
 * @description: 设备上报数据表达式表 业务层实现类
 */
@Service
@RequiredArgsConstructor
public class DeviceAutoReportExpressionServiceImpl implements DeviceAutoReportExpressionService {

    private final DeviceAutoReportExpressionMapper expressionMapper;


    /**
     * 通过 设备id查询 属性对应表达式
     *
     * @param deviceId
     * @return
     */
    @Override
    public List<DeviceAutoReportExpression> queryListByDeviceId(Integer deviceId) {
        return expressionMapper.selectVoList(new QueryWrapper<DeviceAutoReportExpression>().eq("device_id", deviceId));
    }

    @Override
    public List<DeviceAutoReportExpression> queryList() {
        return expressionMapper.selectVoList(new QueryWrapper<DeviceAutoReportExpression>());
    }
}
