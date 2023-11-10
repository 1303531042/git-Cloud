package com.ruoyi.simulation.automessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 将AutoRequestExpression实现类注册到AutoRequestExpressionRegistry管理
 */
public class AutoRequestExpressionRegistry {
    private static Map<String, Class<? extends  AutoRequestExpression>> implementations = new HashMap<>();

    public static void registerImplementation(String key, Class<? extends  AutoRequestExpression>  clazz) {
        implementations.put(key, clazz);

    }

    public static Class<? extends  AutoRequestExpression> getImplementation(String key) {
        return implementations.get(key);
    }
}