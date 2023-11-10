package com.ruoyi.simulation.domain;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.simulation.model.ThingsModelValueItem;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备对象 iot_device
 *
 * @author kerwincui
 * @date 2021-12-16
 */
public class SimulationDeviceWrapper extends SimulationDevice {

    private SimulationDevice device;

    /**
     * 设备属性map 属性名->属性值
     */
    private final Map<String, Object> propertiesMap = new ConcurrentHashMap<>();


    /**
     * 方法执行->方法对应的值
     */
    private final Map<String, Object> functionActionMap = new ConcurrentHashMap<>();

    /**
     * 触发事件->事件对应的值
     */
    private final Map<String, Object> eventsMap = new ConcurrentHashMap<>();


    public SimulationDeviceWrapper(SimulationDevice device) {
        this.device = device;
        afterPropertiesSet();
    }


    /**
     * 初始化
     */
    public void afterPropertiesSet() {
        initMap(getThingsModel());

    }

    /**
     * 初始化三个map
     *
     * @param thingsModel
     */
    public void initMap(String thingsModel) {
        List<ThingsModelValueItem> items = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(thingsModel);
        jsonObject.forEach((key, value) -> {
            switch (key) {
                case "events":
                    JSONArray eventArray = (JSONArray) value;
                    eventArray.forEach(o -> {
                        JSONObject eventJO = (JSONObject) o;
                        String eventId = (String) eventJO.get("id");
                        ThingsModelValueItem thingsModelValueItem = new ThingsModelValueItem();
                        thingsModelValueItem.setId(eventId);
                        thingsModelValueItem.setShadow("");
                        thingsModelValueItem.setValue("");
                        items.add(thingsModelValueItem);
                        eventsMap.put(eventId, null);
                    });
                    break;
                case "functions":
                    JSONArray functionArray = (JSONArray) value;
                    functionArray.forEach(o -> {
                        JSONObject functionJO = (JSONObject) o;
                        String functionId = (String) functionJO.get("id");
                        ThingsModelValueItem thingsModelValueItem = new ThingsModelValueItem();
                        thingsModelValueItem.setId(functionId);
                        thingsModelValueItem.setShadow("");
                        thingsModelValueItem.setValue("");
                        items.add(thingsModelValueItem);
                        functionActionMap.put(functionId, null);
                    });
                    break;
                case "properties":
                    JSONArray propertyArray = (JSONArray) value;
                    propertyArray.forEach(o -> {
                        JSONObject propertyJO = (JSONObject) o;
                        String propertyId = (String) propertyJO.get("id");
                        ThingsModelValueItem thingsModelValueItem = new ThingsModelValueItem();
                        thingsModelValueItem.setId(propertyId);
                        thingsModelValueItem.setShadow("");
                        thingsModelValueItem.setValue("");
                        items.add(thingsModelValueItem);
                        propertiesMap.put(propertyId, "");
                    });
                    break;
                default:
                    break;
            }

        });
        setThingsModelValue(JSON.toJSONString(items));
    }


    /**
     * 获取对应设备属性
     *
     * @param fieldName
     * @return
     */
    public Object getProperty(String fieldName) {
        return propertiesMap.get(fieldName);
    }

    /**
     * 获取对应执行方法
     *
     * @param methodName
     * @return
     */
    public Object getActionMethod(String methodName) {
        return functionActionMap.get(methodName);
    }

    public Object getEvent(String event) {
        return eventsMap.get(event);
    }

    public Set<String> getPropertyNames() {
        return propertiesMap.keySet();
    }

    public Set<String> getActionNames() {
        return functionActionMap.keySet();
    }

    public Set<String> getEventNames() {
        return eventsMap.keySet();
    }



    public String getThingsModel() {
        return device.getThingsModel();
    }

    public String getThingsModelValue() {
        return device.getThingsModelValue();
    }

    public Integer getId() {
        return device.getId();
    }

    public Integer getProductId() {
        return device.getProductId();
    }

    public String getDeviceName() {
        return device.getDeviceName();
    }

    public String getDeviceCode() {
        return device.getDeviceCode();
    }

    public String getChannelCode() {
        return device.getChannelCode();
    }

    public Integer getStatus() {
        return device.getStatus();
    }

    public String getNetworkIp() {
        return device.getNetworkIp();
    }

    public Date getUpdateTime() {
        return device.getUpdateTime();
    }

    public Date getCreateTime() {
        return device.getCreateTime();
    }


}
