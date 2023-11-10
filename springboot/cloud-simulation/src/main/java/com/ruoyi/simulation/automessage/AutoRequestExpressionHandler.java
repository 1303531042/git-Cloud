package com.ruoyi.simulation.automessage;

import com.ruoyi.simulation.domain.DeviceAutoReportExpression;
import com.ruoyi.simulation.service.DeviceAutoReportExpressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: KUN
 * @date: 2023/8/23
 * @description:
 */
@Component
@RequiredArgsConstructor
public class AutoRequestExpressionHandler {
    private final DeviceAutoReportExpressionService expressionService;

    /**
     * key: 设备ID
     * value：该设备所有主动上传属性的表达式列表
     */
    private final Map<Integer, List<DeviceAutoReportExpression>> deviceIdToDeviceAutoReportExpressionsMap = new HashMap<>();

    @PostConstruct
    public void init() {
        //初始化map
        List<DeviceAutoReportExpression> expressionList = expressionService.queryList();
        expressionList.forEach(expression->{
            List<DeviceAutoReportExpression> list = deviceIdToDeviceAutoReportExpressionsMap.computeIfAbsent(expression.getDeviceId(), key -> new ArrayList<>());
            list.add(expression);
        });
    }

    public List<DeviceAutoReportExpression> getAutoRequestExpressionListByDeviceCode(int deviceId) {
        List<DeviceAutoReportExpression> expressionList = deviceIdToDeviceAutoReportExpressionsMap.get(deviceId);
        if (expressionList == null) {
            expressionList = new ArrayList<>();
        }
        return expressionList;
    }
}
