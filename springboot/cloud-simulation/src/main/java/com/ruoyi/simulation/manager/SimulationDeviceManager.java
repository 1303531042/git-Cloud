package com.ruoyi.simulation.manager;

import com.alibaba.fastjson.JSON;
import com.ruoyi.simulation.automessage.AutoRequestExpression;
import com.ruoyi.simulation.automessage.AutoRequestExpressionHandler;
import com.ruoyi.simulation.automessage.AutoRequestExpressionRegistry;
import com.ruoyi.simulation.automessage.AutoRequestExpressionWrapper;
import com.ruoyi.simulation.compoment.SimulationInterface;
import com.ruoyi.simulation.domain.DeviceAutoReportExpression;
import com.ruoyi.simulation.domain.SimulationDevice;
import com.ruoyi.simulation.domain.SimulationDeviceWrapper;
import com.ruoyi.simulation.model.DeviceCodeAndProperty;
import com.ruoyi.simulation.model.SimulationDeviceRequestTemplate;
import com.ruoyi.simulation.service.SimulationDeviceService;
import com.ruoyi.simulation.thread.SimulationThreadPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description: 模拟设备统一管理
 */
@Component
@RequiredArgsConstructor
@DependsOn("mqttAutoRequestExpression")
public class SimulationDeviceManager implements Executor, InitializingBean {
    /**
     * key: deviceCode
     * value: simulationDevice
     */
    private final Map<String, SimulationDeviceWrapper> deviceCodeToSimulationDeviceMap = new HashMap<>();
    /**
     * key: simulationDevice
     * value: productID
     */
    private final Map<SimulationDeviceWrapper, Integer> simulationDeviceToProductIDMap = new HashMap<>();

    /**
     * key: productID
     * value: simulationDevices
     */
    private final Map<Integer, List<SimulationDeviceWrapper>> productToSimulationDevices = new HashMap<>();

    /**
     * 设备属性对应的map 平台主动rpc获取属性数据
     * key: simulationDevice device code
     * value: SimulationDeviceRequestTemplate 列表
     */
    private final Map<String, List<SimulationDeviceRequestTemplate>> propertyRequestTemplateMap = new HashMap<>();
    /**
     * 设备属性对应的map 设备主动rpc上传数据
     * key: simulationDevice device code
     * value: SimulationDeviceRequestTemplate 列表
     */
    private final Map<String, Map<String, AutoRequestExpression>> autoRequestExpressionMap = new HashMap<>();
    private final Map<AutoRequestExpression, DeviceCodeAndProperty> expressionToDeviceCodeAndProperty = new HashMap<>();

    /**
     * 设备动作对应的map
     * key: simulationDevice device code
     * value: SimulationDeviceRequestTemplate 列表
     */
    private final Map<String, List<SimulationDeviceRequestTemplate>> actionRequestTemplateMap = new HashMap<>();


    private final SimulationInterfaceManager simulationInterfaceManager;
    private final SimulationThreadPool simulationThreadPool;
    private final SimulationDeviceService simulationDeviceService;

    private final AutoRequestExpressionHandler autoRequestExpressionHandler;
    /**
     * 读出simulation device表中的所有模拟设备 放入类缓存
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        simulationDeviceService.queryAll().forEach(this::attach);
    }

    public List<SimulationDeviceWrapper> getDeviceForProductID(Integer productID) {
        return productToSimulationDevices.get(productID);
    }

    public Map<String, AutoRequestExpression> getExpressionForDevcieCode(String deviceCode) {
        return autoRequestExpressionMap.get(deviceCode);
    }

    public DeviceCodeAndProperty getDeviceCodeAndPropertyForExpression(AutoRequestExpression autoRequestExpression) {
        return expressionToDeviceCodeAndProperty.get(autoRequestExpression);
    }

    /**
     * 将设备加入 manager 管理
     *
     * @param simulationDevice 设备
     */
    public void attach(SimulationDevice simulationDevice) {
        String deviceCode = simulationDevice.getDeviceCode();
        Integer productId = simulationDevice.getProductId();
        SimulationDeviceWrapper device = simulationDevice.tpWrapper();
        deviceCodeToSimulationDeviceMap.put(deviceCode, device);
        simulationDeviceToProductIDMap.put(device, productId);
        deviceAutoExpression(device);
        List<SimulationDeviceWrapper> simulationDeviceWrappers = productToSimulationDevices.computeIfAbsent(productId, key -> new ArrayList<>());
        simulationDeviceWrappers.add(device);
        deviceToRequestTemplate(device);
    }

    /**
     * 获取设备自动上报时的属性对应表达式
     */
    private void deviceAutoExpression(SimulationDeviceWrapper deviceWrapper) {

        //设备主动上报时 属性对应的表达式
        List<DeviceAutoReportExpression> deviceAutoReportExpressions = autoRequestExpressionHandler.getAutoRequestExpressionListByDeviceCode(deviceWrapper.getId());
        Map<String, AutoRequestExpression> propertyAutoRequestExpressionMap = autoRequestExpressionMap.computeIfAbsent(deviceWrapper.getDeviceCode(), key -> new HashMap<>());
        deviceAutoReportExpressions.forEach(deviceAutoReportExpression -> {
            String expressionWrapperJSON = deviceAutoReportExpression.getExpression();
            AutoRequestExpressionWrapper autoRequestExpressionWrapper = null;
            try {
                autoRequestExpressionWrapper = JSON.parseObject(expressionWrapperJSON, AutoRequestExpressionWrapper.class);
            } catch (Exception e) {
                System.out.println(deviceWrapper.getId()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println(expressionWrapperJSON+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
            AutoRequestExpression expression = null;
            Class<? extends AutoRequestExpression> implementation = AutoRequestExpressionRegistry.getImplementation(autoRequestExpressionWrapper.getAutoRequestExpressionClassPath());
            if (implementation == null) {
                throw new RuntimeException();
            }
            expression = JSON.parseObject(autoRequestExpressionWrapper.getAutoRequestExpressionJSON(), implementation);
            String property = deviceAutoReportExpression.getIdentity();
            propertyAutoRequestExpressionMap.put(property, expression);

            DeviceCodeAndProperty deviceCodeAndProperty = new DeviceCodeAndProperty();
            deviceCodeAndProperty.setDeviceWrapper(deviceWrapper);
            deviceCodeAndProperty.setProperty(property);
            expressionToDeviceCodeAndProperty.put(expression, deviceCodeAndProperty);
        });
    }


    /**
     * 将设备 属性动作 转换成  SimulationDeviceRequestTemplate
     *
     * @param
     */
    private void deviceToRequestTemplate(SimulationDeviceWrapper device) {
//        String channelCode = device.getChannelCode();
//        Integer productId = device.getProductId();
//        String deviceCode = device.getDeviceCode();
//
//        List<SimulationDeviceRequestTemplate> propertyTemplates = device.getPropertyNames().stream()
//                .filter(propertyName -> simulationInterfaceManager.getResultEnumForProductProperty(productId, propertyName) != null)
//                .map(
//                        propertyName -> {
//                            SimulationDeviceRequestTemplate template = new SimulationDeviceRequestTemplate();
//                            template.setDeviceCode(deviceCode);
//                            template.setName(propertyName);
//                            template.setChannelCode(channelCode);
//                            template.setSimulationInterface(simulationInterfaceManager.getSimulationByProductID(productId));
//                            template.setRequestEnum(simulationInterfaceManager.getResultEnumForProductProperty(productId, propertyName));
//                            template.setParamsMap(simulationInterfaceManager.getProductRequestEnumParams(productId, simulationInterfaceManager.getResultEnumForProductProperty(productId, propertyName)));
//                            template.setResultType(simulationInterfaceManager.getTypeEnumToProductProperty(productId, propertyName));
//                            return template;
//                        }
//                ).collect(Collectors.toList());
//        propertyRequestTemplateMap.put(deviceCode, propertyTemplates);
//
//
//        List<SimulationDeviceRequestTemplate> actionTemplates = device.getActionNames().stream()
//                .filter(actionName -> simulationInterfaceManager.getResultEnumForProductActionName(productId, actionName) != null)
//                .map(
//                        actionName -> {
//                            SimulationDeviceRequestTemplate template = new SimulationDeviceRequestTemplate();
//                            template.setDeviceCode(deviceCode);
//                            template.setName(actionName);
//                            template.setChannelCode(channelCode);
//                            template.setSimulationInterface(simulationInterfaceManager.getSimulationByProductID(productId));
//                            template.setRequestEnum(simulationInterfaceManager.getResultEnumForProductActionName(productId, actionName));
//                            template.setParamsMap(simulationInterfaceManager.getProductRequestEnumParams(productId, simulationInterfaceManager.getResultEnumForProductActionName(productId, actionName)));
//                            template.setResultType(simulationInterfaceManager.getTypeEnumToProductAction(productId, actionName));
//                            return template;
//                        }
//                ).collect(Collectors.toList());
//
//        propertyRequestTemplateMap.put(deviceCode, propertyTemplates);
//        actionRequestTemplateMap.put(deviceCode, actionTemplates);

    }


    /**
     * 由线程池执行 任务
     */
    @Override
    public void execute(Runnable command) {
        simulationThreadPool.execute(command);
    }

    /**
     * 定时任务的目的是查我们的执行线程是否正常工作  如果没有则新生成线程 测试阶段 两分钟执行一次 设备属性对应的 template，由线程池执行 任务
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void propertyAction() {
        propertyRequestTemplateMap
                .values()
                .forEach(ts -> {
                    ts.forEach(t -> {
                        execute(() -> {
                            try {
                                SimulationInterface<?> simulationInterface = t.getSimulationInterface();
                                simulationInterface.requestAndHandlerResponse(t);
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                        });
                    });
                });

    }


}
