package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.CoreConst;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.component.ByteToMessageDecoderServerComponent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;

/**
 * Dtu 简单通道解码器
 * @see DtuMessageDecoder
 * @see DtuFirstDeviceSnPackageHandler dtu的第一包上报设备编号
 */
public abstract class DtuDecoderServerComponent<M extends ServerMessage> extends ByteToMessageDecoderServerComponent<M> implements DtuMessageDecoder<M> {

    private DtuMessageAware<M> dtuMessageAwareDelegation;

    public DtuDecoderServerComponent(ConnectProperties connectProperties) {
        super(connectProperties);
        this.dtuMessageAwareDelegation = new DefaultDtuMessageAware<>(this);
    }

    public DtuDecoderServerComponent(ConnectProperties connectProperties, DtuMessageAware<M> dtuMessageAwareDelegation) {
        super(connectProperties);
        this.setDtuMessageAwareDelegation(dtuMessageAwareDelegation);
    }

    /**
     * @param ctx
     * @param buf
     * @return
     */
    @Override
    public M doTcpDecode(ChannelHandlerContext ctx, ByteBuf buf) {
        Attribute attr = ctx.channel().attr(CoreConst.EQUIP_CODE);
        if (attr.get() == null) { // 设备编号不存在说明是DTU第一次上报设备编号
            return buildDeviceSnMessage(ctx, buf);
        } else {
            byte[] message = new byte[buf.readableBytes()];

            // 解码异常 #I51ASQ
            buf.slice().readBytes(message);
            // 如果dtu除了上报设备编号的报文外, dtu自身还有其他的功能(心跳或者AT指令)
            ServerMessage serverMessage = getDtuMessageAwareDelegation().decodeBefore((String) attr.get(), message, buf);

            if (serverMessage != null) {
                //  交由下一个业务处理器处理
                serverMessage.setChannelId(ctx.channel().id().asShortText());
                return (M) serverMessage.readBuild();
            } else {
                return null; // 等待下一个报文的到来
            }
        }
    }

    @Override
    public DtuMessageAware<M> getDtuMessageAwareDelegation() {
        return dtuMessageAwareDelegation;
    }

    public void setDtuMessageAwareDelegation(DtuMessageAware<M> dtuMessageAwareDelegation) {
        this.dtuMessageAwareDelegation = dtuMessageAwareDelegation;
        if(this.dtuMessageAwareDelegation instanceof DefaultDtuMessageAware) {
            if(((DefaultDtuMessageAware<M>) this.dtuMessageAwareDelegation).getDecoder() == null) {
                ((DefaultDtuMessageAware<M>) this.dtuMessageAwareDelegation).setDecoder(this);
            }
        }
    }
}
