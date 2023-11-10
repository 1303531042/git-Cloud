package com.ruoyi.common.satoken.core.service;

import cn.dev33.satoken.stp.StpInterface;
import com.ruoyi.common.satoken.utils.LoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限管理实现类
 *
 * @author qianyf
 */
@Slf4j
@Component
public class SaPermissionImpl implements StpInterface {

    /**
     * 获取菜单权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>(LoginHelper.getLoginUser().getMenuPermission());
    }

    /**
     * 获取角色权限列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<>(LoginHelper.getLoginUser().getRolePermission());

    }
}
