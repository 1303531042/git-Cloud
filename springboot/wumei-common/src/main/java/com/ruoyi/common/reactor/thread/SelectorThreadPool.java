package com.ruoyi.common.reactor.thread;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther: KUN
 * @date: 2023/4/25
 * @description: Selector线程池
 */
@RestController
public class SelectorThreadPool implements Executor {
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2, 4
            , 120, TimeUnit.SECONDS
            , new LinkedBlockingQueue<>(), new SelectorThreadFactory(), new ThreadPoolExecutor.AbortPolicy());



    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
