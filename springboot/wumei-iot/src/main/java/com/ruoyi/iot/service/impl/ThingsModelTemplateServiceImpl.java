package com.ruoyi.iot.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.iot.mapper.ThingsModelTemplateMapper;
import com.ruoyi.iot.domain.ThingsModelTemplate;
import com.ruoyi.iot.service.IThingsModelTemplateService;


/**
 * 通用物模型Service业务层处理
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Service
public class ThingsModelTemplateServiceImpl implements IThingsModelTemplateService 
{
    @Autowired
    private ThingsModelTemplateMapper thingsModelTemplateMapper;

    /**
     * 查询通用物模型
     * 
     * @param
     * @return 通用物模型
     */
    @Override
    public ThingsModelTemplate selectThingsModelTemplateByTemplateId(ThingsModelTemplate thingsModelTemplate)
    {
        return thingsModelTemplateMapper.selectVoOne(new QueryWrapper<ThingsModelTemplate>().setEntity(thingsModelTemplate));
    }

    /**
     * 查询通用物模型列表
     * 
     * @param thingsModelTemplate 通用物模型
     * @return 通用物模型
     */
    @Override
    public List<ThingsModelTemplate> selectThingsModelTemplateList(ThingsModelTemplate thingsModelTemplate)
    {
        return thingsModelTemplateMapper.selectVoList(new QueryWrapper<ThingsModelTemplate>().setEntity(thingsModelTemplate));
    }

    /**
     * 新增通用物模型
     * 
     * @param thingsModelTemplate 通用物模型
     * @return 结果
     */
    @Override
    public int insertThingsModelTemplate(ThingsModelTemplate thingsModelTemplate)
    {
        return thingsModelTemplateMapper.insert(thingsModelTemplate);
    }

    /**
     * 修改通用物模型
     * 
     * @param thingsModelTemplate 通用物模型
     * @return 结果
     */
    @Override
    public int updateThingsModelTemplate(ThingsModelTemplate thingsModelTemplate)
    {
        thingsModelTemplate.setUpdateTime(DateUtils.getNowDate());
        return thingsModelTemplateMapper.update(thingsModelTemplate, new UpdateWrapper<ThingsModelTemplate>().eq("template_id", thingsModelTemplate.getTemplateId()));
    }

    /**
     * 批量删除通用物模型
     * 
     * @param templateIds 需要删除的通用物模型主键
     * @return 结果
     */
    @Override
    public int deleteThingsModelTemplateByTemplateIds(Long[] templateIds)
    {
        return thingsModelTemplateMapper.delete(new QueryWrapper<ThingsModelTemplate>().in("template_id", templateIds));
    }

    /**
     * 删除通用物模型信息
     * 
     * @param templateId 通用物模型主键
     * @return 结果
     */
    @Override
    public int deleteThingsModelTemplateByTemplateId(Long templateId)
    {
        return thingsModelTemplateMapper.delete(new QueryWrapper<ThingsModelTemplate>().eq("template_id", templateId));
    }

}
