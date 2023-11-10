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
public class BrokerClearThreadPool implements Executor {
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 3
            , 120, TimeUnit.SECONDS
            , new LinkedBlockingQueue<>(), new BrokerClearThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());



    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
