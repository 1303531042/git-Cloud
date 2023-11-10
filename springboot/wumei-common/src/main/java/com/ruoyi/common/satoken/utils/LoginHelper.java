package com.ruoyi.common.satoken.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.model.LoginUserEntity;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.bean.BeanUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app
 * deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致
 * 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author qianyf
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    public static final String JOIN_CODE = ":";
    public static final String LOGIN_USER_KEY = "LoginUserEntity";

    /**
     * 登录系统
     *
     * @param loginUser 登录用户信息
     */
    public static void login(LoginUserEntity loginUser) {
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        StpUtil.login(loginUser.getUserId());
        setLoginUser(loginUser);
    }

    /**
     * 登录系统 基于 设备类型
     * 针对相同用户体系不同设备
     *
     * @param loginUser 登录用户信息
     */
    public static void loginByDevice(LoginUserEntity loginUser) {
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        StpUtil.login(loginUser.getUserId());
        setLoginUser(loginUser);
    }


    /**
     * 设置用户数据(多级缓存)
     */
    public static void setLoginUser(LoginUserEntity loginUser) {
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

    /**
     * 获取用户(多级缓存)
     */
    public static LoginUserEntity getLoginUser() {
        LoginUserEntity loginUser = (LoginUserEntity) SaHolder.getStorage().get(LOGIN_USER_KEY);
        if (loginUser != null) {
            return loginUser;
        }else {
            loginUser = new LoginUserEntity();
        }
        loginUser = (LoginUserEntity) StpUtil.getTokenSession().get(LOGIN_USER_KEY);
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        return loginUser;
    }
    /**
     * 获取用户id
     */
    public static Long getUserId() {
        LoginUserEntity loginUser = getLoginUser();
        if (ObjectUtil.isNull(loginUser)) {
//            String loginId = StpUtil.getLoginIdAsString();
//            String userId = null;
//            for (UserType value : UserType.values()) {
//                if (StringUtils.contains(loginId, value.getUserId())) {
//                    String[] strs = StringUtils.split(loginId, JOIN_CODE);
//                    // 用户id在总是在最后
//                    userId = strs[strs.length - 1];
//                }
//            }
//            if (StringUtils.isBlank(userId)) {
            throw new UtilException("获取不到登录用户");
//            }
//            return Long.parseLong(userId);
        }
        return loginUser.getUserId();
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return getLoginUser().getDeptId();
    }

    /**
     * 获取用户账户
     */
    public static Long getUsername() {
        return getLoginUser().getUser().getUserId();
    }
    public static Long getTenantId() {
        return getLoginUser().getTenantId();
    }

    /**
     * 获取会话类型
     */
//    public static UserType getUserType() {
//        String loginId = StpUtil.getLoginIdAsString();
//        return UserType.getUserType(loginId);
//    }

//    /**
//     * 获取账户类型
//     *
//     * @return 账户类型
//     */
//    public static Integer getAccountType() {
//        return getLoginUser().getType();
//    }

//    /**
//     * 关联对象Id
//     * @return 关联对象Id
//     */
//    public static Long getRelaId() {
//        return getLoginUser().getRelaId();
//    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return UserConstants.ADMIN_ID.equals(userId);
    }

    public static boolean isAdmin() {
        return isAdmin(getUserId());
    }

}
