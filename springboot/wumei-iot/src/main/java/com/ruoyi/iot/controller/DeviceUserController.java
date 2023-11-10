package com.ruoyi.iot.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.iot.domain.DeviceUser;
import com.ruoyi.iot.service.IDeviceUserService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备用户Controller
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Api(tags = "设备用户")
@RestController
@RequestMapping("/iot/deviceUser")
public class DeviceUserController extends BaseController
{
    @Autowired
    private IDeviceUserService deviceUserService;

    /**
     * 查询设备用户列表
     */
    @GetMapping("/list")
    @ApiOperation("设备用户分页列表")
    @SaCheckPermission("iot:device:list")
    public TableDataInfo list(DeviceUser deviceUser)
    {
        startPage();
        List<DeviceUser> list = deviceUserService.selectDeviceUserList(deviceUser);
        return getDataTable(list);
    }

    /**
     * 获取设备用户详细信息 根据deviceId 查询的话可能会查出多个
     */
    @GetMapping(value = "/{deviceId}")
    @ApiOperation("获取设备用户详情")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId)
    {
        return AjaxResult.success(deviceUserService.selectDeviceUserByDeviceId(deviceId));
    }

    /**
     * 获取设备用户详细信息 双主键 device_id 和 user_id
     */
    @GetMapping(value = "/{deviceId}/{userId}")
    @ApiOperation("获取设备用户详情,根据用户id 和 设备id")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId, @PathVariable("userId") Long userId)
    {
        return AjaxResult.success(deviceUserService.selectDeviceUserByDeviceIdAndUserId(deviceId, userId));
    }

    /**
     * 新增设备用户
     */
    @Log(title = "设备用户", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("添加设备用户")
    @SaCheckPermission("iot:device:share")
    public AjaxResult add(@RequestBody DeviceUser deviceUser)
    {
        return toAjax(deviceUserService.insertDeviceUser(deviceUser));
    }

    /**
     * 新增多个设备用户
     */
    @Log(title = "设备用户", businessType = BusinessType.INSERT)
    @PostMapping("/addDeviceUsers")
    @ApiOperation("批量添加设备用户")
    @SaCheckPermission("iot:device:share")
    public AjaxResult addDeviceUsers(@RequestBody List<DeviceUser> deviceUsers)
    {
        return toAjax(deviceUserService.insertDeviceUserList(deviceUsers));
    }

    /**
     * 修改设备用户
     */
    @ApiOperation("修改设备用户")
    @Log(title = "设备用户", businessType = BusinessType.UPDATE)
    @PutMapping
    @SaCheckPermission("iot:device:edit")
    public AjaxResult edit(@RequestBody DeviceUser deviceUser)
    {
        return toAjax(deviceUserService.updateDeviceUser(deviceUser));
    }


    /**
     * 删除设备用户
     */
    @ApiOperation("删除设备用户")
    @Log(title = "设备用户", businessType = BusinessType.DELETE)
	@DeleteMapping
    @SaCheckPermission("iot:device:remove")
    public AjaxResult remove(@RequestBody DeviceUser deviceUser)
    {
        int count=deviceUserService.deleteDeviceUser(deviceUser);
        if(count==0){
            return AjaxResult.error("设备所有者不能删除");
        }else{
            return AjaxResult.success();
        }
    }
}
