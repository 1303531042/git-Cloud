package com.ruoyi.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.ruoyi.common.mybatisplus.tenant.TenantAtomicBoolean;
import com.ruoyi.common.satoken.utils.LoginHelper;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @auther: KUN
 * @date: 2023/7/27
 * @description: MyBatisPlus 配置类
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfiguration {


    /**
     * add interceptor.
     * 添加拦截器 多租户插件 和 分页插件
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(TenantLineHandler tenantLineHandler) {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler));

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        //添加多租户插件
        return interceptor;
    }

    /**
     * 配置自动填充
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                // 数据类型要与fileName一致
                this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
                if (!TenantAtomicBoolean.isTrue()) {
                    this.strictInsertFill(metaObject, "createBy", String.class, LoginHelper.getLoginUser().getUser().getUserName());
                    this.strictInsertFill(metaObject, "userId", Long.class, LoginHelper.getLoginUser().getUser().getUserId());
                    this.strictInsertFill(metaObject, "tenantId", Long.class, LoginHelper.getLoginUser().getUser().getUserId());
                    this.strictInsertFill(metaObject, "tenantName", String.class, LoginHelper.getLoginUser().getUser().getUserName());
                }
            }


            @Override
            public void updateFill(MetaObject metaObject) {
                // 数据类型要与fileName一致
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
                if (!TenantAtomicBoolean.isTrue()) {
                    this.strictUpdateFill(metaObject, "updateBy", String.class, LoginHelper.getLoginUser().getUser().getUserName());
                }
            }

        };
    }


}
