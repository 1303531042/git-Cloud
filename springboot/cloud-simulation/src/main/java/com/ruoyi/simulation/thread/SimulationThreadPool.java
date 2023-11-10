package com.ruoyi.simulation.thread;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther: KUN
 * @date: 2023/4/25
 * @description: 模拟层线程池
 */
@RestController
public class SimulationThreadPool implements Executor {
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            8, 32
            , 120, TimeUnit.SECONDS
            , new LinkedBlockingQueue<>(), new SimulationThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());



    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
