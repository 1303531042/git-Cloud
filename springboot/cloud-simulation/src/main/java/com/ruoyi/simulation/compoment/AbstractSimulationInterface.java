package com.ruoyi.simulation.compoment;


import com.ruoyi.simulation.Exceptions.action.ParamNotFoundException;
import com.ruoyi.simulation.annotations.ParamExplain;
import com.ruoyi.simulation.annotations.SimulationClass;
import com.ruoyi.simulation.annotations.SimulationMethod;
import com.ruoyi.simulation.domain.vo.ParamExplainVo;
import com.ruoyi.simulation.enums.RequestEnum;
import com.ruoyi.simulation.enums.TypeEnum;
import com.ruoyi.simulation.manager.RequestEnumManager;
import com.ruoyi.simulation.model.SimulationDeviceRequestTemplate;
import com.ruoyi.simulation.utils.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/3/28
 * @description: 模拟数据层抽象实现类
 */
public abstract class AbstractSimulationInterface<T extends RequestEnum> implements SimulationInterface<T>,SimulationChannelControl {


    /**
     * key: ResultEnum 方法枚举
     * value： simulationMethod 静态方法
     */
    private final Map<T, Method> methodEnumToMethod = new HashMap<>();

    /**
     * key: ResultEnum 方法枚举
     * value: simulationMethod 静态方法的参数列表
     */
    private final Map<T, List<Parameter>> requestEnumToParametersMap = new HashMap<>();






    /**
     * 需要注入所有的参数
     * key: ResultEnum
     * value: simulationMethod 静态方法的参数说明
     */
    private final Map<T, List<ParamExplainVo>> requestEnumToParamExplainVosMap = new HashMap<>();

    /**
     * key：requestEnum
     * value：requestEnum的参数id列表
     */
    private final Map<T, List<Integer>> requestEnumToParamIDs = new HashMap<>();


    public AbstractSimulationInterface() {
        try {
            initComponentClass();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回扫描 simulation interface类所在的包名
     */
    protected abstract String checkPackageName();

    /**
     * 指定扫描包的位置 获取到该 simulation interface 的 simulation class的位置 扫描该class 获取所有 simulation method
     */
    private void initComponentClass() throws Exception {
        Class<?> clazz = scopeComponentClass(checkPackageName());
        if (clazz == null) {
            throw new Exception();
        }
        //将该simulation使用的requestEnum管理起来
        RequestEnumManager.initSimulationRequestEnums((Class<? extends SimulationInterface<?>>) this.getClass());
        scopeSimulationMethods(clazz);
    }

    /**
     * 扫描到该组件到 SimulationClass 实现类
     */
    private Class<?> scopeComponentClass(String packageName) {
        Set<Class<?>> classes = ReflectionUtils.getClasses(packageName, true);
        Class<?> clazz = this.getClass();
        List<Class<?>> simulations = classes.stream()
                .filter(c -> {
                    SimulationClass simulationClass = c.getAnnotation(SimulationClass.class);
                    return simulationClass != null && simulationClass.value() == clazz;
                }).collect(Collectors.toList());
        if (simulations.size() > 1) {
            throw new RuntimeException();
        }
        return simulations.get(0);
    }

    /**
     * 扫描到该组件到 SimulationClass 实现类的 simulationMethod 映射到各个map中
     */
    private void scopeSimulationMethods(Class<?> clazz) {
        List<Method> methodList = ReflectionUtils.getAllMethods(clazz);
        methodList
                .stream()
                .filter(m -> m.getAnnotation(SimulationMethod.class) != null)
                .forEach(m->{
                    SimulationMethod simulationMethod = m.getAnnotation(SimulationMethod.class);
                    int code = simulationMethod.code();
                    T resultEnum = (T) RequestEnumManager.getRequestEnum( this.getClass(), code);
                    methodEnumToMethod.put(resultEnum, m);
                    requestEnumToParametersMap.put(resultEnum, Arrays.asList(m.getParameters()));
                    Arrays.stream(m.getParameters())
                            .forEach(parameter -> {
                                //协议可用方法的参数上都必须有@ParamExplain
                                ParamExplain paramExplain = parameter.getAnnotation(ParamExplain.class);
                                if (paramExplain == null) {
                                    throw new IllegalArgumentException(getSimulationName() + "协议可用静态方法: " + m.getName() + "未给参数添加@ParamExplain注解");
                                }

                                ParamExplainVo paramExplainVo = new ParamExplainVo();
                                paramExplainVo.setId(paramExplain.id());
                                paramExplainVo.setExplain(paramExplain.explain());
                                paramExplainVo.setClassType(TypeEnum.INSTANCE(parameter.getClass()));
                                List<ParamExplainVo> vos = requestEnumToParamExplainVosMap.computeIfAbsent(resultEnum, k -> new ArrayList<>());
                                //协议可用静态方法 @ParamExplain id必须唯一
                                if (vos.stream().map(ParamExplainVo::getId).collect(Collectors.toList()).contains(paramExplain.id())) {
                                    throw new IllegalArgumentException(getSimulationName() + "协议可用静态方法: " + m.getName() + "方法@ParamExplain id不唯一");
                                }
                                vos.add(paramExplainVo);

                                List<Integer> list = requestEnumToParamIDs.computeIfAbsent(resultEnum, k -> new ArrayList<>());
                                list.add(paramExplain.id());
                            });
                });
    }

    /**
     * 通过 requestEnum获取到 可执行到静态方法
     */
    private Method requestEnumToMethod(T requestEnum) {
        return methodEnumToMethod.get(requestEnum);
    }

    /**
     * 通过 requestEnum获取到 可执行到静态方法的参数列表
     */
    private List<Parameter> requestEnumToParameters(T requestEnum) {
        return requestEnumToParametersMap.get(requestEnum);
    }



    /**
     * 获取调用RPC所有需要注入的参数内容
     *
     */
    @Override
    public List<ParamExplainVo> getParamExplainVos(RequestEnum requestEnum) {
        return requestEnumToParamExplainVosMap.get(requestEnum);
    }


    /**
     * 发送请求并处理请求
     *
     */
    @Override
    public void requestAndHandlerResponse(SimulationDeviceRequestTemplate template) {
        RequestEnum requestEnum =template.getRequestEnum();
        Map<Integer, Object> params = template.getParamsMap();
        try {
            handlerResponse(template, request(requestEnum, params));
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送请求
     */
    public Object request(RequestEnum requestEnum, Map<Integer, Object> params) throws InvocationTargetException {
        return ReflectionUtils.invokeMethod(null, methodEnumToMethod.get(requestEnum), requestEnumToParamIDs.get(requestEnum).stream().map(id -> {
            if (params.get(id) == null) {
                throw new ParamNotFoundException(id);
            } else {
                return params.get(id);
            }
        }).toArray());
    }

    /**
     * 处理请求的返回值
     */
    public abstract void handlerResponse(SimulationDeviceRequestTemplate template, Object result);
}
