package com.ruoyi.common.influxdb;

import java.util.function.Supplier;

public class FluxQueryContainerFactory<T extends Measudirection> {
    private ThreadLocal<Supplier<FluxQueryContainer<T>>> containerThreadLocal = ThreadLocal.withInitial(() -> FluxQueryContainer::new);

    public FluxQueryContainer<T> createContainer() {
        return containerThreadLocal.get().get();
    }
}
