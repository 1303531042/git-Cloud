package com.ruoyi.iot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.iot.domain.Group;
import com.ruoyi.iot.model.DeviceGroupInput;
import com.ruoyi.iot.service.IGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备分组Controller
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Api(tags = "设备分组")
@RestController
@RequestMapping("/iot/group")
public class GroupController extends BaseController
{
    @Autowired
    private IGroupService groupService;

    /**
     * 查询设备分组列表
     */
    @GetMapping("/list")
    @ApiOperation("分组分页列表")
    @SaCheckPermission("iot:group:list")
    public TableDataInfo list(Group group)
    {
        startPage();
        return getDataTable(groupService.selectGroupList(group));
    }

    /**
     * 导出设备分组列表
     */
    @Log(title = "分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出分组")
    @SaCheckPermission("iot:group:export")
    public void export(HttpServletResponse response, Group group)
    {
        List<Group> list = groupService.selectGroupList(group);
        ExcelUtil<Group> util = new ExcelUtil<Group>(Group.class);
        util.exportExcel(response, list, "设备分组数据");
    }

    /**
     * 获取设备分组详细信息
     */
    @GetMapping(value = "/{groupId}")
    @ApiOperation("获取分组详情")
    @SaCheckPermission("iot:group:query")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return AjaxResult.success(groupService.selectGroupByGroupId(groupId));
    }

    /**
     * 获取分组下的所有关联设备ID数组
     */
    @GetMapping(value = "/getDeviceIds/{groupId}")
    @ApiOperation("获取分组下的所有关联设备ID数组")
    @SaCheckPermission("iot:group:query")
    public AjaxResult getDeviceIds(@PathVariable("groupId") Long groupId)
    {
        return AjaxResult.success(groupService.selectDeviceIdsByGroupId(groupId));
    }

    /**
     * 新增设备分组
     */
    @Log(title = "分组", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("添加分组")
    @SaCheckPermission("iot:group:add")
    public AjaxResult add(@RequestBody Group group)
    {
        return toAjax(groupService.insertGroup(group));
    }

    /**
     * 更新分组下的关联设备
     * @param input
     * @return
     */
    @Log(title = "设备分组", businessType = BusinessType.UPDATE)
    @PutMapping("/updateDeviceGroups")
    @ApiOperation("更新分组下的关联设备")
    @SaCheckPermission("iot:group:edit")
    public AjaxResult updateDeviceGroups(@RequestBody DeviceGroupInput input){
        return toAjax(groupService.updateDeviceGroups(input));
    }

    /**
     * 修改设备分组
     */
    @Log(title = "分组", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改分组")
    @SaCheckPermission("iot:group:edit")
    public AjaxResult edit(@RequestBody Group group)
    {
        return toAjax(groupService.updateGroup(group));
    }

    /**
     * 删除设备分组
     */
    @Log(title = "分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    @ApiOperation("批量删除设备分组")
    @SaCheckPermission("iot:group:remove")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(groupService.deleteGroupByGroupIds(groupIds));
    }
}
