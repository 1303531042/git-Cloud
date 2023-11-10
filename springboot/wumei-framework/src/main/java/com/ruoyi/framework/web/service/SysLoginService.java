package com.ruoyi.framework.web.service;


import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUserEntity;
import com.ruoyi.common.enums.HttpStatusEnum;
import com.ruoyi.common.exception.user.LoginException;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
@RequiredArgsConstructor
public class SysLoginService {

    private final ISysUserService userService;

    private final ISysConfigService configService;

    private final ISysMenuService iSysMenuService;


    /**
     * 登录验证
     *
     * @param userName   用户id
     * @param password 密码
     * @param code     验证码
     * @return 结果
     */
    public Map<String,String> login(String userName, String password, String code, String uuid) {
//        // 验证码开关
//        if (configService.selectCaptchaOnOff()) {
//            CheckUtils.validateCaptcha(code, uuid);
//        }
        return socialLogin(userName, password);

    }

    /**
     * 第三方验证后，调用登录方法
     *
     * @param userName 用户名
     * @param password 密码
     * @return token
     */
    public Map<String, String> socialLogin(String userName, String password) {

        SysUser user = userService.selectUserByUserName(userName);
        if (BCrypt.checkpw(password, user.getPassword())) {
            StpUtil.login(user.getUserId());
        } else {
            throw new LoginException(HttpStatusEnum.ERROR_PASSWORD.getCode(), user.getUserId() + HttpStatusEnum.ERROR_PASSWORD.getMessage());
        }
        LoginUserEntity loginUser = new LoginUserEntity();
        loginUser.setUserId(user.getUserId());
        loginUser.setTenantId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setToken(StpUtil.getTokenValue());
        loginUser.setLoginTime(new Date().getTime());
        loginUser.setUser(user);
        loginUser.setMenuPermission(iSysMenuService.selectMenuList(user.getUserId()).stream().map(SysMenu::getPerms).collect(Collectors.toList()));
        loginUser.setPermissions(Arrays.stream(userService.selectUserRoleGroup(userName).split(",")).collect(Collectors.toSet()));
        LoginHelper.setLoginUser(loginUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        return map;

    }

}
