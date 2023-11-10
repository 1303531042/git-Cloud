package com.ruoyi.common.satoken.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限安全配置
 *
 * @author qianyf
 */
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注解拦截器
        registry.addInterceptor(new SaInterceptor(handle-> StpUtil.checkLogin())).addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/logout");
    }
//
//    /**
//     * 校验是否从网关转发
//     */
//    @Bean
//    public SaServletFilter getSaServletFilter() {
//        return new SaServletFilter()
//            .addInclude("/**")
//            .addExclude("/actuator/**")
//            .setAuth(obj -> SaSameUtil.checkCurrentRequestToken())
//            .setError(e -> SaResult.error("认证失败，无法访问系统资源").setCode(HttpStatus.UNAUTHORIZED));
//    }

}
