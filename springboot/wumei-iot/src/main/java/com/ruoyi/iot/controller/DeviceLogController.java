package com.ruoyi.iot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.iot.domain.DeviceLog;
import com.ruoyi.iot.model.MonitorModel;
import com.ruoyi.iot.model.dto.DeviceEventDto;
import com.ruoyi.iot.model.dto.HistoryDto;
import com.ruoyi.iot.service.IDeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备日志Controller
 * 
 * @author kerwincui
 * @date 2022-01-13
 */
@RestController
@RequestMapping("/iot/deviceLog")
public class DeviceLogController extends BaseController
{
    @Autowired
    private IDeviceLogService deviceLogService;

    /**
     * 查询设备日志列表
     */
    @GetMapping("/list")
    @SaCheckPermission("iot:device:list")
    public TableDataInfo list(DeviceLog deviceLog)
    {
        startPage();
        List<DeviceLog> list = deviceLogService.selectDeviceLogList(deviceLog);
        return getDataTable(list);
    }

    /**
     * 查询设备的监测数据
     */
    @GetMapping("/monitor")
    @SaCheckPermission("iot:device:list")
    public TableDataInfo monitorList(DeviceLog deviceLog)
    {
        List<MonitorModel> list = deviceLogService.selectMonitorList(deviceLog);
        return getDataTable(list);
    }

    @GetMapping("/history")
    @SaCheckPermission("iot:device:list")
    public AjaxResult historyList(HistoryDto historyDto) {
        return AjaxResult.success(deviceLogService.selectHistoryList(historyDto));
    }

    /**
     * 获取设备事件日志详细信息
     */
    @GetMapping(value = "/event")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getDeviceEventLog(DeviceEventDto deviceEventDto)
    {
        return AjaxResult.success(deviceLogService.selectDeviceEvent(deviceEventDto));
    }

    /**
     * 导出设备日志列表
     */
    @Log(title = "设备日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @SaCheckPermission("iot:device:export")
    public void export(HttpServletResponse response, DeviceLog deviceLog)
    {
        List<DeviceLog> list = deviceLogService.selectDeviceLogList(deviceLog);
        ExcelUtil<DeviceLog> util = new ExcelUtil<DeviceLog>(DeviceLog.class);
        util.exportExcel(response, list, "设备日志数据");
    }

    /**
     * 获取设备日志详细信息
     */
    @GetMapping(value = "/{logId}")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getInfo(@PathVariable("logId") Long logId)
    {
        return AjaxResult.success(deviceLogService.selectDeviceLogByLogId(logId));
    }

    /**
     * 新增设备日志
     */
    @Log(title = "设备日志", businessType = BusinessType.INSERT)
    @PostMapping
    @SaCheckPermission("iot:device:add")
    public AjaxResult add(@RequestBody DeviceLog deviceLog)
    {
//        return toAjax(deviceLogService.insertDeviceLog(deviceLog));
        return null;
    }

    /**
     * 修改设备日志
     */
    @Log(title = "设备日志", businessType = BusinessType.UPDATE)
    @PutMapping
    @SaCheckPermission("iot:device:edit")
    public AjaxResult edit(@RequestBody DeviceLog deviceLog)
    {
        return toAjax(deviceLogService.updateDeviceLog(deviceLog));
    }

    /**
     * 删除设备日志
     */
    @Log(title = "设备日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logIds}")
    @SaCheckPermission("iot:device:remove")
    public AjaxResult remove(@PathVariable Long[] logIds)
    {
        return toAjax(deviceLogService.deleteDeviceLogByLogIds(logIds));
    }
}
