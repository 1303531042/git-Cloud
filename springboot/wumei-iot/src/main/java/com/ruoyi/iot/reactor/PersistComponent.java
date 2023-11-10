package com.ruoyi.iot.reactor;

import com.ruoyi.common.mybatisplus.tenant.TenantAtomicBoolean;
import com.ruoyi.common.reactor.EventChannelHandler;
import com.ruoyi.common.reactor.component.BaseComponent;
import com.ruoyi.common.reactor.event.DeviceDataExportIotEvent;
import com.ruoyi.common.reactor.event.IotEvent;
import com.ruoyi.common.reactor.event.IotEventWrapper;
import com.ruoyi.common.reactor.selectorkey.EventSelectKey;
import com.ruoyi.iot.domain.Device;
import com.ruoyi.iot.model.ThingsModels.ThingsModelValueRemarkItem;
import com.ruoyi.iot.model.ThingsModels.ThingsModelValuesInput;
import com.ruoyi.iot.model.bo.DeviceReportBo;
import com.ruoyi.iot.service.IDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/6/27
 * @description: 设备数据持久化组件
 */
@Component
@Slf4j
public class PersistComponent extends BaseComponent implements BeanFactoryAware {


    /**
     * 注册数据接收事件
     * @return
     */
    @Override
    protected int registerOps() {
        return EventSelectKey.OP_ACCEPCTED_DATA;
    }


    @Override
    protected int registerChannelID() {
        return 1;
    }

    @Override
    protected String registerExplain() {
        return "设备数据持久化组件";
    }

    @Override
    protected String registerHandlerName() {
        return "persistChannelHandler";
    }


    @Component("persistChannelHandler")
    @RequiredArgsConstructor
    public class PersistChannelHandler extends EventChannelHandler {

        private final IDeviceService deviceService;

        @Override
        protected void process(List<IotEvent>iotEventList) {
            try {
                long startTime = System.currentTimeMillis();
//                processAcceptData(iotEventList);
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
//                log.warn(Thread.currentThread().getName() + "  任务：" + iotEventList + "执行时间======>" + time);
//                switch (iotEvent.getEventOps()) {
//                    case EventSelectKey.OP_ACCEPCTED_DATA:
//
//                        break;
//                    default:
//                        break;
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        /**
         * 事件分流 批处理
         * @param iotEventList
         * @return
         */
        @Override
        protected List<List<IotEvent>> stream(List<IotEvent> iotEventList) {
            List<List<IotEvent>> iotEventStream = new CopyOnWriteArrayList<>();
            int batchSize = 500; // 每个子列表的大小
            int totalSize = iotEventList.size();
            int numOfBatches = (int) Math.ceil((double) totalSize / batchSize);

            for (int i = 0; i < numOfBatches; i++) {
                int fromIndex = i * batchSize;
                int toIndex = Math.min(fromIndex + batchSize, totalSize);
                List<IotEvent> batch = iotEventList.subList(fromIndex, toIndex);
                iotEventStream.add(new CopyOnWriteArrayList<>(batch));
            }

            return iotEventStream;
        }

        private void processAcceptData(List<IotEvent> iotEvents) {
            long startTime = System.currentTimeMillis();
            try {
                TenantAtomicBoolean.setTrue();

                List<Long> deviceIds = iotEvents
                        .stream()
                        .filter(iotEvent -> iotEvent instanceof DeviceDataExportIotEvent)
                        .map(iotEvent -> {
                            DeviceDataExportIotEvent event = (DeviceDataExportIotEvent) iotEvent;
                            return Long.parseLong(event.getDeviceId());
                        }).collect(Collectors.toList());


                Map<Long, Device> deviceMap = deviceService.queryDeviceByDeviceIds(deviceIds)
                        .stream()
                        .collect(Collectors.toMap(Device::getDeviceId, Function.identity()));



                List<DeviceReportBo> deviceReportBos = iotEvents
                        .stream()
                        .filter(iotEvent -> iotEvent instanceof DeviceDataExportIotEvent)
                        .map(iotEvent -> {
                            DeviceDataExportIotEvent event = (DeviceDataExportIotEvent) iotEvent;
                            Device device = deviceMap.get(Long.parseLong(event.getDeviceId()));
                            ThingsModelValueRemarkItem item = new ThingsModelValueRemarkItem();
                            item.setId(event.getIdentity());
                            item.setValue(event.getState());
                            List<ThingsModelValueRemarkItem> list = new ArrayList<>();
                            list.add(item);
                            ThingsModelValuesInput input = new ThingsModelValuesInput();
                            input.setDeviceId(device.getDeviceId());
                            input.setProductId(device.getProductId());
                            input.setDeviceNumber(device.getSerialNumber());
                            input.setCreateDate(event.getCreateTime());
                            input.setThingsModelValueRemarkItem(list);
                            DeviceReportBo deviceReportBo = new DeviceReportBo();
                            deviceReportBo.setInput(input);
                            deviceReportBo.setType(event.getUpType());
                            deviceReportBo.setShadow(false);
                            return deviceReportBo;
                        }).collect(Collectors.toList());


                deviceService.reportDeviceThingsModelValues(deviceReportBos);




            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                TenantAtomicBoolean.setFalse();
            }
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.warn(Thread.currentThread().getName() + "设备数据持久化组件任务数：" + iotEvents.size() + "  执行时间======>" + time);



        }
    }





}
