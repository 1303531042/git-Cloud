package com.ruoyi.gateway.client.handle;


import com.ruoyi.gateway.BusinessAction;
import com.ruoyi.gateway.CoreConst;
import com.ruoyi.gateway.IotProtocolFactory;
import com.ruoyi.gateway.ProtocolException;
import com.ruoyi.gateway.client.*;
import com.ruoyi.gateway.client.protocol.ClientInitiativeProtocol;
import com.ruoyi.gateway.client.protocol.ServerInitiativeProtocol;
import com.ruoyi.gateway.codec.filter.CombinedFilter;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.event.*;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.protocol.NoneDealProtocol;
import com.ruoyi.gateway.protocol.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.ruoyi.gateway.CoreConst.CLIENT_KEY;

/**
 * 客户端业务处理器
 */
public class ClientServiceHandler extends SimpleChannelInboundHandler<ClientMessage> {

    private ClientComponent clientComponent;
    private Logger logger = LoggerFactory.getLogger(ClientServiceHandler.class);


    public ClientServiceHandler(ClientComponent clientComponent) {
        this.clientComponent = clientComponent;
        if (this.clientComponent == null) {
            throw new IllegalArgumentException("ClientProtocolHandle必填参数[ClientComponent]");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientMessage msg) throws Exception {
        try {
            String componentName = clientComponent.getName();
            final IotProtocolFactory protocolFactory = clientComponent.protocolFactory();
            if (protocolFactory == null) {
                String deviceSn = msg.getHead().getEquipCode();
                logger.error("客户端协议处理({}) 协议工厂不存在 - 设备编号: {}", componentName, deviceSn);
                return;
            }

            Protocol protocol = protocolFactory.getProtocol(msg);
            if (protocol == null) {
                String messageId = msg.getMessageId();
                String deviceSn = msg.getHead() != null ? msg.getHead().getEquipCode() : null;
                logger.warn("客户端协议处理({}) 获取不到协议 - 设备编号: {} - messageId: {}"
                        , componentName, deviceSn, messageId);
                return;
            }
            // 声明此协议不需要处理
            if (protocol instanceof NoneDealProtocol) {
                return;
            }

            // 服务端响应给客户端
            if (protocol instanceof ClientInitiativeProtocol) {
                ((ClientInitiativeProtocol) protocol).buildResponseMessage(msg);

                // 服务端主动调用客户端
            } else if (protocol instanceof ServerInitiativeProtocol) {
                ServerInitiativeProtocol serverInitiativeProtocol = (ServerInitiativeProtocol) protocol;
                serverInitiativeProtocol.setClientKey(ctx.channel().attr(CLIENT_KEY).get());
                serverInitiativeProtocol.buildRequestMessage();
            } else {
                logger.error("客户端协议处理({}) 错误协议 协议类型: {} - 说明: [{}]必须是[{}]的子类", componentName
                        , protocol.protocolType(), protocol.getClass().getSimpleName(), AbstractClientProtocol.class.getSimpleName());
            }

            // 需要执行业务
            if (protocol instanceof BusinessAction) {
                // 执行业务, 阻塞太长的业务需要自行处理, 否则将影响并发量
                final AbstractProtocol response = ((BusinessAction)
                        protocol).exec(IotClientBootstrap.businessFactory);

                // 响应给服务端
                if (response != null) {
                    ctx.channel().writeAndFlush(response);
                }
            }

        } catch (Exception e) {
            this.exceptionCaught(ctx, e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("客户端处理({}) 处理异常 - 错误信息: {} - 处理方式：已发送异常事件[{}], 创建监听器[{}]来监听异常事件"
                , clientComponent.getName(), cause.getMessage(),
                ExceptionEvent.class.getSimpleName(), ExceptionEventListener.class.getSimpleName(), cause);

        if (cause instanceof ProtocolException) {
            Object protocol = ((ProtocolException) cause).getProtocol();
            if (protocol instanceof ClientProtocol) {
                IotClientBootstrap.publishApplicationEvent(new ProtocolExceptionEvent((Protocol) protocol, cause.getCause()));
                return;
            }
        }

        IotClientBootstrap.publishApplicationEvent(new ExceptionEvent(cause, null));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Optional<CombinedFilter> filter = clientComponent.getFilter();
        if (!filter.orElse(CombinedFilter.DEFAULT).isActivation(ctx.channel(), clientComponent)) {
            ctx.channel().close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端重连
        ConnectProperties config = ctx.channel().attr(CLIENT_KEY).get();
        IotClient client = clientComponent.getClient(config);

        /**
         * 1. 此处必须判断客户端是否存在(因为有可能已经被移除)
         * 2. 判断是否是因为调用接口disconnect导致的断开连接 如果是则不进行重连
         */
        if (client instanceof SocketClient) {
            Attribute<Boolean> attr = ctx.channel().attr(CoreConst.CLIENT_CLOSED_DISCONNECT);
            // 如果是调用了disconnect接口导致的断线则不进行重连
            if(attr.get() == null) {
                ((SocketClient) client).reconnection();
            }

            // 发布掉线事件
            IotClientBootstrap.publishApplicationEvent(new ClientStatusEvent(client, ClientStatus.offline, clientComponent));
        }
    }
}
