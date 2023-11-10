package com.ruoyi.iot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.mybatisplus.page.PageQuery;
import com.ruoyi.common.mybatisplus.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.iot.domain.Device;
import com.ruoyi.iot.domain.Product;
import com.ruoyi.iot.energy.domain.AreaDevice;
import com.ruoyi.iot.energy.model.dto.AddTemplatesDto;
import com.ruoyi.iot.energy.model.dto.EditSimpleDeviceDto;
import com.ruoyi.iot.energy.model.vo.SimpleDeviceTemplateVo;
import com.ruoyi.iot.energy.service.AreaDeviceService;
import com.ruoyi.iot.model.DeviceRelateUserInput;
import com.ruoyi.iot.model.dto.SimpleDeviceDto;
import com.ruoyi.iot.service.IDeviceService;
import com.ruoyi.iot.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.dev.ReSave;
import org.quartz.SchedulerException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.excel.read.listener.ReadListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备Controller
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Api(tags = "设备管理")
@RestController
@RequestMapping("/iot/device")
@RequiredArgsConstructor
public class DeviceController extends BaseController
{
    private final IDeviceService deviceService;

    private final AreaDeviceService areaDeviceService;
    private final IProductService iProductService;



    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    @ApiOperation("设备分页列表")
    @SaCheckPermission("iot:device:list")
    public TableDataInfo<Device> list(Device device, PageQuery pageQuery) {

        return deviceService.queryPageListByEntity(device, pageQuery);
    }

    /**
     * 查询未分配授权码设备列表
     */
    @GetMapping("/unAuthlist")
    @ApiOperation("设备分页列表")
    @SaCheckPermission("iot:device:list")
    public TableDataInfo<Device> unAuthlist(Device device, PageQuery pageQuery)
    {
        return deviceService.queryPageListByEntity(device, pageQuery);
    }

    /**
     * 查询分组可添加设备
     */
    @GetMapping("/listByGroup")
    @ApiOperation("查询分组可添加设备分页列表")
    @SaCheckPermission("iot:device:list")
    public TableDataInfo<Device> listByGroup(Device device, PageQuery pageQuery)
    {
        return deviceService.queryPageListByEntity(device, pageQuery);

    }

//    /**
//     * 查询设备简短列表，主页列表数据
//     */
//    @GetMapping("/shortList")
//    @ApiOperation("设备分页简短列表")
//    @SaCheckPermission("iot:device:list")
//    public TableDataInfo shortList(Device device)
//    {
//        startPage();
//        return getDataTable(deviceService.selectDeviceShortList(device));
//    }
//
//    /**
//     * 查询所有设备简短列表
//     */
//    @GetMapping("/all")
//    @ApiOperation("查询所有设备简短列表")
//    @SaCheckPermission("iot:device:list")
//    public TableDataInfo allShortList()
//    {
//        return getDataTable(deviceService.selectAllDeviceShortList());
//    }

    /**
     * 导出设备列表
     */
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出设备")
    @SaCheckPermission("iot:device:export")
    public void export(HttpServletResponse response, Device device)
    {
        List<Device> list = deviceService.selectDeviceList(device);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        util.exportExcel(response, list, "设备数据");
    }

    /**
     * 获取设备详细信息
     */
    @GetMapping(value = "/{deviceId}")
    @ApiOperation("获取设备详情")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId)
    {
        return AjaxResult.success(deviceService.selectDeviceByDeviceId(deviceId));
    }



    /**
     * 根据设备编号详细信息
     */
    @GetMapping(value = "/getDeviceBySerialNumber/{serialNumber}")
    @ApiOperation("根据设备编号获取设备详情")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getInfoBySerialNumber(@PathVariable("serialNumber") String serialNumber)
    {
        return AjaxResult.success(deviceService.selectDeviceBySerialNumber(serialNumber));
    }

    /**
     * 获取设备统计信息
     */
    @GetMapping(value = "/statistic")
    @ApiOperation("获取设备统计信息")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getDeviceStatistic()
    {
        return AjaxResult.success(deviceService.selectDeviceStatistic());
    }

    /**
     * 获取设备详细信息
     */
    @GetMapping(value = "/runningStatus/{deviceId}")
    @ApiOperation("获取设备详情和运行状态")
    @SaCheckPermission("iot:device:query")
    public AjaxResult getRunningStatusInfo(@PathVariable("deviceId") Long deviceId)
    {
        return AjaxResult.success(deviceService.selectDeviceRunningStatusByDeviceId(deviceId));
    }

    /**
     * 新增设备
     */
    @Log(title = "添加设备", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("添加设备")
    @SaCheckPermission("iot:device:add")
    public AjaxResult add(@RequestBody Device device)
    {
        return AjaxResult.success(deviceService.insertDevice(device));
    }

    /**
     * 设备关联用户
     */
    @Log(title = "设备关联用户", businessType = BusinessType.UPDATE)
    @PostMapping("/relateUser")
    @ApiOperation("设备关联用户")
    @SaCheckPermission("iot:device:add")
    public AjaxResult relateUser(@RequestBody DeviceRelateUserInput deviceRelateUserInput)
    {
        if(deviceRelateUserInput.getUserId()==0 || deviceRelateUserInput.getUserId()==null){
            return AjaxResult.error("用户ID不能为空");
        }
        if(deviceRelateUserInput.getDeviceNumberAndProductIds()==null || deviceRelateUserInput.getDeviceNumberAndProductIds().size()==0){
            return AjaxResult.error("设备编号和产品ID不能为空");
        }
        return deviceService.deviceRelateUser(deviceRelateUserInput);
    }

    /**
     * 修改设备
     */
    @Log(title = "修改设备", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改设备")
    @SaCheckPermission("iot:device:edit")
    public AjaxResult edit(@RequestBody Device device)
    {
        return deviceService.updateDevice(device);
    }

    /**
     * 重置设备状态
     */
    @Log(title = "重置设备状态", businessType = BusinessType.UPDATE)
    @PutMapping("/reset/{serialNumber}")
    @ApiOperation("重置设备状态")
    @SaCheckPermission("iot:device:edit")
    public AjaxResult resetDeviceStatus(@PathVariable String serialNumber)
    {
        Device device=new Device();
        device.setSerialNumber(serialNumber);
        return toAjax(deviceService.resetDeviceStatus(device.getSerialNumber()));
    }

    /**
     * 删除设备
     */
    @Log(title = "删除设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deviceIds}")
    @ApiOperation("批量删除设备")
    @SaCheckPermission("iot:device:remove")
    public AjaxResult remove(@PathVariable List<Long> deviceIds) throws SchedulerException {
        return toAjax(deviceService.deleteDeviceByDeviceIds(deviceIds));
    }

    /**
     * 生成设备编号
     */
    @GetMapping("/generator")
    @ApiOperation("生成设备编号")
    @SaCheckPermission("iot:device:edit")
    public AjaxResult generatorDeviceNum() {
        return AjaxResult.success("操作成功", deviceService.generationDeviceNum());
    }

    @PostMapping("/add/simpledevice")
    public AjaxResult addSimpleDevice(@RequestBody SimpleDeviceDto simpleDeviceDto) {
        //创建设备things model value
        Product product = iProductService.selectProductByProductId(simpleDeviceDto.getProductId().longValue());
        //生成设备uuid
        UUID uuid = UUID.randomUUID();
        Device device = new Device();
        device.setDeviceName(simpleDeviceDto.getDeviceName());
        device.setIsShadow(1);
        if (simpleDeviceDto.getLatitude() != null) {
            device.setLatitude(BigDecimal.valueOf(simpleDeviceDto.getLatitude()));

        }
        if (simpleDeviceDto.getLongitude() != null) {
            device.setLongitude(BigDecimal.valueOf(simpleDeviceDto.getLongitude()));
        }
        device.setProductId(simpleDeviceDto.getProductId().longValue());
        device.setProductName(product.getProductName());
        device.setDeviceName(simpleDeviceDto.getDeviceName());
        device.setSerialNumber(uuid.toString());
        //设备设备为未激活 管理员去添加设备协议信息
        device.setStatus(1);
        device.setIsShadow(1);
        deviceService.insertDevice(device);
        device = deviceService.selectDeviceBySerialNumber(uuid.toString());
        AreaDevice areaDevice = new AreaDevice();
        areaDevice.setDeviceId(device.getDeviceId().intValue());
        areaDevice.setProductId(device.getProductId().intValue());
        areaDevice.setAreaId(simpleDeviceDto.getAreaId());
        areaDeviceService.saveAreaDevice(areaDevice);
        return AjaxResult.success();
    }

    @PostMapping("/edit/simpledevice")
    public AjaxResult editSimpleDevice( @RequestBody  EditSimpleDeviceDto editSimpleDeviceDto) {
        Device device = new Device();
        device.setDeviceName(editSimpleDeviceDto.getDeviceName());
        device.setLatitude(BigDecimal.valueOf(editSimpleDeviceDto.getLatitude()));
        device.setLongitude(BigDecimal.valueOf(editSimpleDeviceDto.getLongitude()));
        device.setDeviceId(editSimpleDeviceDto.getDeviceId().longValue());
        deviceService.updateDevice(device);
        AreaDevice areaDevice = new AreaDevice();
        areaDevice.setDeviceId(editSimpleDeviceDto.getDeviceId());
        areaDevice.setAreaId(editSimpleDeviceDto.getAreaId());
        areaDeviceService.updateAreaDevice(areaDevice);
        return AjaxResult.success();
    }


    @PostMapping(value = "/add/templates",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AjaxResult addTemplates(@ModelAttribute AddTemplatesDto addTemplatesDto) throws IOException {

        InputStream inputStream = addTemplatesDto.getFile().getInputStream();
        //将excel 行数据转换成SimpleDeviceTemplateVo
        List<SimpleDeviceTemplateVo> resultList = new ArrayList<>();
        try {
            EasyExcel.read(inputStream)
                    .head(SimpleDeviceTemplateVo.class)
                    .registerReadListener(new AnalysisEventListener<SimpleDeviceTemplateVo>() {
                        @Override
                        public void invoke(SimpleDeviceTemplateVo simpleDeviceTemplateVo, AnalysisContext analysisContext) {
                            resultList.add(simpleDeviceTemplateVo);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                        }
                    })
                    .sheet()
                    .doRead();
        } catch (Exception e) {
            return AjaxResult.error();
        } finally {
            inputStream.close();
        }
        //SimpleDeviceTemplateVo转换成device
        Product product = iProductService.selectProductByProductId(addTemplatesDto.getProductId().longValue());
        String thingsModelDefaultValue = JSON.toJSONString(deviceService.getThingsModelDefaultValue(product.getProductId()));
        List<Device> deviceList = resultList.stream().map(result -> {
            Device device = new Device();
            device.setProductId(product.getProductId());
            device.setProductName(product.getProductName());
            device.setIsShadow(1);
            device.setSerialNumber(UUID.randomUUID().toString());
            device.setStatus(1);
            device.setThingsModelValue(thingsModelDefaultValue);
            device.setDeviceName(result.getDeviceName());
            if (result.getLongitude() != null) {
                device.setLongitude(BigDecimal.valueOf(result.getLongitude()));
            }
            if (result.getLatitude() != null) {
                device.setLatitude(BigDecimal.valueOf(result.getLatitude()));
            }
            return device;
        }).collect(Collectors.toList());
        deviceService.insertDevices(deviceList);
        List<String> serialNumbers = deviceList.stream().map(Device::getSerialNumber).collect(Collectors.toList());
        deviceList = deviceService.selectDeviceListBySerialNumbers(serialNumbers);
        List<AreaDevice> areaDeviceList = deviceList.stream().map(device -> {
            AreaDevice areaDevice = new AreaDevice();
            areaDevice.setDeviceId(device.getDeviceId().intValue());
            areaDevice.setProductId(device.getProductId().intValue());
            areaDevice.setAreaId(addTemplatesDto.getAreaId());
            return areaDevice;
        }).collect(Collectors.toList());
        areaDeviceService.saveAreaDevices(areaDeviceList);

        return AjaxResult.success();

    }

    @GetMapping("/template")
    @ResponseBody
    public void getTemplate(HttpServletResponse response) throws IOException {
        // 创建对象模板列表
        List<SimpleDeviceTemplateVo> simpleDeviceTemplateVos = new ArrayList<>();
        // 添加示例数据
        SimpleDeviceTemplateVo vo = new SimpleDeviceTemplateVo();
        vo.setDeviceName("设备1");
        vo.setLongitude(116.397128);
        vo.setLatitude(39.916527);
        simpleDeviceTemplateVos.add(vo);

        // 重要! 设置返回格式是excel形式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 设置编码格式
        String fileName = URLEncoder.encode("设备模版", "UTF-8").replaceAll("\\+", "%20");
//        String fileName = "template.xlsx";
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        response.setStatus(HttpServletResponse.SC_OK);

        // 使用EasyExcel将对象模板写入Excel文件并下载
        EasyExcel.write(response.getOutputStream(), SimpleDeviceTemplateVo.class).sheet("创建实体类模板").doWrite(simpleDeviceTemplateVos);
    }


    @PostMapping("/remove/simpledevice")
    public AjaxResult removeSimpleDevice(@RequestBody Long[] deviceIds) {
        List<Long> deviceIdList = Arrays.asList(deviceIds);
        deviceService.deleteDeviceByDeviceIds(deviceIdList);
        areaDeviceService.deleteByDeviceIds(deviceIdList);
        return AjaxResult.success();
    }







}
