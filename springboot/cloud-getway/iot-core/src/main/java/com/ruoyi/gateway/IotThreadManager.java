package com.ruoyi.gateway;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.SystemPropertyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 线程管理
 */
public class IotThreadManager implements InitializingBean, DisposableBean, ApplicationContextAware {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ApplicationContext applicationContext;
    private EventExecutor deviceManageEventExecutor;
    private ThreadPoolTaskScheduler schedulerExecutor;
    private static IotThreadManager threadManager = new IotThreadManager();

    protected IotThreadManager() { }

    public static IotThreadManager instance() {
        return threadManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IotCoreProperties properties = this.applicationContext.getBean(IotCoreProperties.class);

        // 启用服务端
        if(properties.getBossThreadNum() > 0) {
            // IST = Iot Selector Thread
            bossGroup = new NioEventLoopGroup(properties.getBossThreadNum(), new DefaultThreadFactory("IST"));
        }

        int workerThreadNum = properties.getWorkerThreadNum();
        if(workerThreadNum == 0) { // 服务端工作线程组
            workerThreadNum = Math.max(1, SystemPropertyUtil.getInt(
                    "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        }

        if(workerThreadNum > 0) {
            // IWT = Iot Worker Thread
            workerGroup = new NioEventLoopGroup(workerThreadNum, new DefaultThreadFactory("IWT"));
        }

        // IDM = Iot Device Manager
        this.deviceManageEventExecutor = new DefaultEventExecutor(workerGroup, new DefaultThreadFactory("IDM"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        if(bossGroup != null && !workerGroup.isShutdown()) {
            bossGroup.shutdownGracefully();
        }

        if(workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }

        if(!this.deviceManageEventExecutor.isShutdown()) {
            this.deviceManageEventExecutor.shutdownGracefully();
        }
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    /**
     * 设备管理模块使用的调度执行器
     * @return
     */
    public EventExecutor getDeviceManageEventExecutor() {
        return deviceManageEventExecutor;
    }

    public ThreadPoolTaskScheduler getSchedulerExecutor() {
        return schedulerExecutor;
    }

    public void setSchedulerExecutor(ThreadPoolTaskScheduler schedulerExecutor) {
        this.schedulerExecutor = schedulerExecutor;
    }
}
