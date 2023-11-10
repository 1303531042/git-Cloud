package com.ruoyi.simulation.manager;

import com.ruoyi.simulation.compoment.SimulationInterface;
import com.ruoyi.simulation.enums.RequestEnum;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @auther: KUN
 * @date: 2023/7/24
 * @description: 管理requestEnum
 */
@Component
public class RequestEnumManager {

    private static final Map<Class<?>, List<? extends RequestEnum>> requestEnumsMap = new ConcurrentHashMap<>();

    /**
     * 初始化simulation时将其注入到这里
     */
    public static void initSimulationRequestEnums(Class<? extends SimulationInterface<?>> clazz) {

        Class<? extends RequestEnum> clacc = (Class<? extends RequestEnum>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        List<? extends RequestEnum> requestEnums = Arrays.stream(clacc.getEnumConstants()).collect(Collectors.toList());
        requestEnumsMap.put(clazz, requestEnums);
    }

    /**
     * 获取该模拟层可用的RequestEnum列表
     *
     * @param clazz
     * @return
     */
    public static List<? extends RequestEnum> getSimulationRequestEnums(Class<?> clazz) {

        return requestEnumsMap.get(clazz);
    }


    /**
     * 通过code获取到 RequestEnum
     *
     * @param clazz
     * @param code
     * @return
     */
    public static RequestEnum getRequestEnum(Class<?> clazz, int code) {
        List<? extends RequestEnum> requestEnums = requestEnumsMap.get(clazz);
        for (RequestEnum requestEnum : requestEnums) {
            if (requestEnum.getCode() == code) {
                return requestEnum;
            }
        }
        throw new RuntimeException("该code的requestEnum不存在");
    }
}
