package com.ruoyi.iot.energy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.iot.energy.domain.Area;
import com.ruoyi.iot.energy.mapper.AreaMapper;
import com.ruoyi.iot.energy.model.bo.AreaBo;
import com.ruoyi.iot.energy.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/9/5
 * @description: 区域表 服务层实现类
 */
@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaMapper areaMapper;



    /**
     * 根据租户id查询他的区域列表
     * @return
     */
    @Override
    public List<AreaBo> queryAreaListByTenantId() {
        List<AreaBo> areaBos = new ArrayList<>();
        try {
            areaBos = areaMapper.selectVoList(new QueryWrapper<Area>()
                    .eq("visible", 0)
                    .orderByAsc("parent_id")
                    .orderByAsc("order_num")
            ).stream().map(area -> {
                AreaBo bo = new AreaBo();
                bo.setTenantId(area.getTenantId());
                bo.setAreaName(area.getAreaName());
                bo.setOrderNum(area.getOrderNum());
                bo.setId(area.getId());
                bo.setParentId(area.getParentId());
                return bo;
            }).collect(Collectors.toList());

            areaBos = getChildPerms(areaBos, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaBos;
    }

    /**
     * 获取查询该父区域下没有子区域的所有区域的id
     * @param areaId
     * @return
     */
    @Override
    public List<Integer> queryAreaChildPerms(int areaId) {
        List<Integer> result = new ArrayList<>();
        try {
            List<AreaBo> areaBos = areaMapper.selectVoList(new QueryWrapper<Area>()
                    .eq("visible", 0)
                    .orderByAsc("parent_id")
                    .orderByAsc("order_num")
            ).stream().map(area -> {
                AreaBo bo = new AreaBo();
                bo.setTenantId(area.getTenantId());
                bo.setAreaName(area.getAreaName());
                bo.setOrderNum(area.getOrderNum());
                bo.setId(area.getId());
                bo.setParentId(area.getParentId());
                return bo;
            }).collect(Collectors.toList());
            AreaBo targetArea = null;
            for (AreaBo area : areaBos) {
                if (Objects.equals(areaId, area.getId())) {
                    targetArea = area;
                    break;
                }
            }
            if (targetArea != null) {
                findGroundArea(areaBos, targetArea, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




    private void findGroundArea(List<AreaBo> areaBos, AreaBo targetArea, List<Integer> result) {
        if (hasChild(areaBos, targetArea)) {
            List<AreaBo> childPerms = getChildPerms(areaBos, targetArea.getId());
            childPerms.forEach(child -> findGroundArea(areaBos, child, result));
        }
        result.add(targetArea.getId());
    }
    private void getAreaIds(List<Integer> result, List<AreaBo> areaBos) {
        while (areaBos != null &&areaBos.size() != 0) {
            areaBos.forEach(areaBo ->{
                result.add(areaBo.getId());
                getAreaIds(result, areaBo.getChildren());
            });

        }
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    private List<AreaBo> getChildPerms(List<AreaBo> list, int parentId) {
        List<AreaBo> returnList = new ArrayList<>();
        for (Iterator<AreaBo> iterator = list.iterator(); iterator.hasNext(); ) {
            AreaBo t = (AreaBo) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<AreaBo> list, AreaBo t) {
        // 得到子节点列表
        List<AreaBo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (AreaBo tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<AreaBo> getChildList(List<AreaBo> list, AreaBo t) {
        List<AreaBo> tlist = new ArrayList<>();
        Iterator<AreaBo> it = list.iterator();
        while (it.hasNext()) {
            AreaBo n = (AreaBo) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<AreaBo> list, AreaBo t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
