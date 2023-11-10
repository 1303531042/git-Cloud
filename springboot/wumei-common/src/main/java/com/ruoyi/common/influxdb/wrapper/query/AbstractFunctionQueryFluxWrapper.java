package com.ruoyi.common.influxdb.wrapper.query;

import com.ruoyi.common.influxdb.Measudirection;
import com.ruoyi.common.influxdb.enumeration.FluxTemplate;
import com.ruoyi.common.influxdb.enumeration.WindowFunction;
import com.ruoyi.common.influxdb.enumeration.WindowTimeIntervalUnit;
import com.ruoyi.common.influxdb.util.TemplateUtil;

/**
 * @auther: KUN
 * @date: 2023/7/14
 * @description:
 */
public abstract class AbstractFunctionQueryFluxWrapper<W extends Measudirection> extends AbstractContainerQueryFluxWrapper<W> implements FluxFunctionQueryWrapper<W> {


    private boolean hadCheckColumnType = false;

    private boolean usedFunction = false;

    private boolean usedArgFunction = false;


    public AbstractFunctionQueryFluxWrapper(Class<W> MeasudirectionClass) {
        super( MeasudirectionClass);
    }

    /**
     * 检查column是否为number类型
     */
    private void checkColumnType() {
        if (!hadCheckColumnType) {
            checkColumnTypeIsInstanceNumber();
            hadCheckColumnType = true;
        }
    }

    /**
     * @param time     时间
     * @param unit     单位
     * @param function 聚合函数
     * @return
     */
    @Override
    public FluxFunctionQueryWrapper<W> window(Integer time, WindowTimeIntervalUnit unit, WindowFunction function) {
        checkColumnType();
        StringBuilder builder = new StringBuilder(FluxTemplate.WINDOW_FLUX.getValue());
        TemplateUtil.replaceTemplate(builder, "timeValue", Integer.toString(time));
        TemplateUtil.replaceTemplate(builder, "timeUnit", unit.getUnit());
        TemplateUtil.replaceTemplate(builder, "windowFunction", function.getFunction());
        addTemplate(FluxTemplate.WINDOW_FLUX, builder.toString());
        return this;
    }


    /**
     * 累计函数
     *
     * @return
     */
    @Override
    public FluxFunctionQueryWrapper<W> cumulativeSum() {
        checkColumnType();
        function();
        andAddTemplate(FluxTemplate.FUNCTION_FLUX, FluxTemplate.CUMULATIVE_SUM_FLUX.getValue());
        return this;
    }

    /**
     * 非负差异的累计和函数
     *
     * @return
     */
    @Override
    public FluxFunctionQueryWrapper<W> increase() {
        checkColumnType();
        function();
        andAddTemplate(FluxTemplate.FUNCTION_FLUX, FluxTemplate.INCREASE_FLUX.getValue());
        return this;
    }

    /**
     * 非空第一个函数
     *
     * @return
     */
    @Override
    public FluxFunctionQueryWrapper<W> first() {
        checkColumnType();
        function();
        argFunction();
        andAddTemplate(FluxTemplate.FUNCTION_FLUX, FluxTemplate.FIRST_FLUX.getValue());
        return this;
    }

    /**
     * 非空最后一个值
     *
     * @return
     */
    @Override
    public FluxFunctionQueryWrapper<W> last() {
        checkColumnType();
        function();
        argFunction();
        andAddTemplate(FluxTemplate.FUNCTION_FLUX, FluxTemplate.LAST_FLUX.getValue());
        return this;
    }

    /**
     * 平均数函数
     */
    @Override
    public FluxFunctionQueryWrapper<W> mean() {
        checkColumnType();
        function();
        argFunction();
        andAddTemplate(FluxTemplate.FUNCTION_FLUX, FluxTemplate.MEAN_FLUX.getValue());
        return this;
    }

    @Override
    public FluxQueryWrapper<W> difference() {
        checkColumnType();
        function();
        argFunction();
        andAddTemplate(FluxTemplate.FUNCTION_FLUX, FluxTemplate.DIFFERENCE.getValue());
        return this;
    }

    /**
     * 是否使用了方法
     */
    @Override
    public boolean useFunction() {
        return usedFunction;
    }

    @Override
    public boolean useArgFunction() {
        return usedArgFunction;
    }

    private void function() {
        usedFunction = true;
    }
    private void argFunction() {
        usedArgFunction = true;
    }



}
