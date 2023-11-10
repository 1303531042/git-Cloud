package com.ruoyi.gateway.server.protocol;

import com.ruoyi.gateway.server.IotServeBootstrap;
import com.ruoyi.gateway.ProtocolException;
import com.ruoyi.gateway.protocol.ProtocolType;
import com.ruoyi.gateway.consts.ExecStatus;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.component.SocketServerComponent;
import io.netty.channel.Channel;

import java.util.concurrent.TimeUnit;

/**
 * 服务端同步协议<hr>
 *     同一连接同一时刻只能发送一个报文
 * @param <M>
 */
public abstract class ServerInitiativeSyncProtocol<M extends ServerMessage> extends ServerInitiativeProtocol<M>{

    public ServerInitiativeSyncProtocol() {
        this.sync(3000); // 默认同步时间
    }

    @Override
    public void request() throws ProtocolException {
        try {
            this.buildRequestMessage();

            //构建完请求协议必须验证请求数据是否存在
            if(null == requestMessage()) {
                throw new IllegalArgumentException("不存在请求报文");
            }

            //获取客户端管理器
            SocketServerComponent component = IotServeBootstrap.getServerComponent(requestMessage().getClass());
            if(component == null) {
                throw new IllegalArgumentException("获取不到["+responseMessage().getClass().getSimpleName()+"]对应的组件");
            }

            Channel channel = component.getDeviceManager().find(getEquipCode());
            if(channel == null) {
                throw new IllegalStateException("无此设备或设备断线");
            }

            // 死锁校验
            syncDeadValidate(channel);

            // 请求报文处理
            requestMessageHandle(component, channel);

            /**
             * 设备的同步请求必须存储协议否则会出现不能释放锁问题
             * 1. 如果是同步请求那么他们必须关联
             * 2. 如果不使用同步那么必须不能关联
             */
            if(isSyncRequest()) {
                if(!isRelation()) {
                    throw new IllegalStateException("同步执行必须关联协议(将导致锁不能释放)");
                }
            } else {
                if(isRelation()) {
                    throw new IllegalStateException("非同步执行不允许使用关联协议");
                }
            }

            /**
             * 同一连接不能在同一时间执行多次请求
             * 注意：这里和设备之间不能使用异步, 否则同一连接的同步锁将会在业务逻辑上失效
             */
            synchronized (channel) {
                //向客户端写出协议
                if(this.writeAndFlush(component) != ExecStatus.success) {
                    // 执行业务
                    exec(getProtocolHandle());
                    return;
                }

                /**
                 * 设备和程序之间的同步请求
                 * @see #getTimeout() 等待超时
                 */
                if(isSyncRequest()) {
                    // 阻塞线程等待
                    boolean await = getDownLatch().await(getTimeout(), TimeUnit.MILLISECONDS);
                    if(!await) {
                        this.execTimeoutHandle(component);
                    }
                }

                // 执行业务
                exec(getProtocolHandle());
            }

        } catch (InterruptedException e) {
            throw new ProtocolException(e, this);
        } catch (Exception e) {
            if(e instanceof ProtocolException) {
                throw e;
            } else {
                throw new ProtocolException(e.getMessage(), e);
            }
        }
    }

    /**
     * 做一些请求报文对象{@link ServerMessage}的初始化工作
     * @param component
     * @param channel
     */
    protected void requestMessageHandle(SocketServerComponent component, Channel channel) {
        super.requestMessageHandle(component, channel);
        if(this.requestMessage().getHead().getMessageId() == null) {
            this.requestMessage().getHead().setMessageId(channel.id().asShortText());
        }
    }

    @Override
    public abstract ProtocolType protocolType();
}
