//package com.ruoyi.web.controller.system;
//
//import com.alibaba.fastjson.JSON;
//import com.ruoyi.common.reactor.EventBroker;
//import com.ruoyi.common.reactor.event.DeviceDataExportIotEvent;
//import com.ruoyi.iot.service.IDeviceLogService;
//import com.ruoyi.web.eneity.dto.StatesDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
//@Component
//@RocketMQMessageListener(topic = "states_data", consumerGroup = "cloud_platform")
//@Slf4j
//@RequiredArgsConstructor
//public class DataPullMessageListener implements RocketMQListener<String> {
//
//    @Resource
//    private final IDeviceLogService deviceLogService;
//
//    @Override
//    public void onMessage(String message) {
//        try {
//            log.info("Received message:{} ",JSON.parse(message));
//            if (message != null && !message.equals("null")) {
//                log.info("接到设备推送数据");
//                List<StatesDTO> statesDTOS = JSON.parseArray(message, StatesDTO.class);
//                statesDTOS.stream()
//                        .map(statesDTO -> {
//                            DeviceDataExportIotEvent event = new DeviceDataExportIotEvent();
//                            event.setState(statesDTO.getState());
//                            event.setName(statesDTO.getName());
//                            event.setUpType(1);
//                            event.setIdentity(statesDTO.getIdentity());
//                            event.setDeviceId(statesDTO.getDeviceId());
//                            event.setCreateTime(new Date(statesDTO.getTimestamp().getTime()));
//                            return event;
//                        }).forEach(EventBroker::publishEvent);
//
//                //设备数据上传事件
//
//
////                List<DeviceLogBO> deviceLogs = statesDTOS.stream().map(
////                        statesDTO -> {
////                            DeviceLogBO deviceLog = new DeviceLogBO();
////                            deviceLog.setIdentity(statesDTO.getIdentity());
////                            deviceLog.setLogValue(statesDTO.getState());
////                            deviceLog.setDeviceId(Long.valueOf(statesDTO.getDeviceId()));
////                            deviceLog.setDeviceName(statesDTO.getName());
////                            deviceLog.setLogType(1);
////                            deviceLog.setMode(2);
////                            deviceLog.setCreateTime(new Date(statesDTO.getTimestamp().getTime()));
////                            //需要重写
////                            deviceLog.setTenantId(1L);
////                            deviceLog.setUserId(1L);
////                            deviceLog.setUserName("admin");
////                            deviceLog.setTenantName("admin");
////                            return deviceLog;
////                        }
////                ).collect(Collectors.toList());
////                deviceLogs.forEach(deviceLogService::insertDeviceLog);
////                log.info("存储成功");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//}