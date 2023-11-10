package com.ruoyi.common.reactor.thread;

import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @auther: KUN
 * @date: 2023/4/25
 * @description: Selector线程池
 */
@RestController
public class EventHandlerThreadPool implements Executor {
    private final LinkedBlockingQueue queue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            64, 128
            , 120, TimeUnit.SECONDS
            , queue, new EventHandlerThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());





    @Override
    public synchronized void execute(Runnable command) {
        if (threadPoolExecutor.isShutdown()||threadPoolExecutor.isTerminated()) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    64, 128
                    , 120, TimeUnit.SECONDS
                    , queue, new EventHandlerThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());

        }
        threadPoolExecutor.execute(command);


    }
}
