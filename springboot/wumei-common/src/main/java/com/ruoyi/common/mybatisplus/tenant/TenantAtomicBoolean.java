package com.ruoyi.common.mybatisplus.tenant;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @auther: KUN
 * @date: 2023/8/23
 * @description: 网关上传数据开启锁
 */
public class TenantAtomicBoolean{
    private static final ThreadLocal<AtomicBoolean> TenantThreadLocal = new ThreadLocal<>();

    private static AtomicBoolean get() {
        AtomicBoolean atomicBoolean = TenantThreadLocal.get();
        if (atomicBoolean == null) {
            atomicBoolean = new AtomicBoolean(false);
            TenantThreadLocal.set(atomicBoolean);
        }
        return atomicBoolean;
    }
    public static void setTrue() {
        AtomicBoolean atomicBoolean = get();
        atomicBoolean.set(true);
    }
    public static void setFalse() {
        AtomicBoolean atomicBoolean = get();
        atomicBoolean.set(false);
    }

    public static boolean isTrue() {
        return get().get();

    }
}
