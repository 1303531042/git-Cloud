package com.ruoyi.common.mybatisplus.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.ruoyi.common.mybatisplus.MybatisPlusConfig;
import com.ruoyi.common.satoken.utils.LoginHelper;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @auther: KUN
 * @date: 2023/8/29
 * @description:
 */
@Component
public class MyTenantLineHandler implements TenantLineHandler {
    @Autowired
    private MybatisPlusConfig tenantConfiguration;

    private final HashSet<String> ignoreTableSet = new HashSet<>();

    @PostConstruct
    public void init() {
        String[] split = tenantConfiguration.getIgnoreTables().split(";");
        ignoreTableSet.addAll(Arrays.asList(split));
    }

    /**
     * 获取租户id
     * @return
     */
    @Override
    public Expression getTenantId() {
        return new LongValue(LoginHelper.getTenantId());
    }

    /**
     * 租户id 列名
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        return tenantConfiguration.getTenantIdColumn();
    }

    /**
     * 每次调用sql都会进入这个方法
     * @param tableName
     * @return
     */
    @Override
    public boolean ignoreTable(String tableName) {
        //首先忽略我们配置的所有需要忽略租户的表
        if (ignoreTableSet.contains(tableName)) {
            return true;
        }
        //网关上传数据  或  租户为1则为管理员可查看所有信息
        return TenantAtomicBoolean.isTrue() || LoginHelper.isAdmin();
    }

    @Override
    public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        return TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
    }
}
