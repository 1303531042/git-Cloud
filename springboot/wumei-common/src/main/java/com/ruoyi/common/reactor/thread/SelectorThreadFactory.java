package com.ruoyi.common.reactor.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * @auther: KUN
 * @date: 2023/4/25
 * @description: Selector 线程工厂
 */
public class SelectorThreadFactory implements ThreadFactory{

    private volatile int counter;
    private final String name="Selector-ThreadFactory";
    private List<String> stats = new ArrayList<>();
    @Override
    public Thread newThread(Runnable run) {
        Thread t = new Thread(run, name + "-Thread-" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on%s\n" ,t.getId() ,t.getName() ,new Date()));
        return t;
    }

    public String getStas() {
        StringBuilder builder = new StringBuilder();
        stats.stream().filter(Objects::nonNull).forEach(builder::append);
        return builder.toString();
    }

}
