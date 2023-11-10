package com.ruoyi.gateway.client.handle;

import com.ruoyi.gateway.client.ClientProxyServerHandle;
import com.ruoyi.gateway.client.ClientProxyServerProtocol;
import com.ruoyi.gateway.client.ParamResolver;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 客户端请求方法映射控制器
 * @see ClientProxyServerHandle#handle(ClientProxyServerProtocol)
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface IotCtrl {

    /**
     * 名称
     * @return
     */
    String value();

    /**
     * 此处理方法的参数解析
     */
    Class<? extends ParamResolver> resolver() default JsonResolver.class;
}
