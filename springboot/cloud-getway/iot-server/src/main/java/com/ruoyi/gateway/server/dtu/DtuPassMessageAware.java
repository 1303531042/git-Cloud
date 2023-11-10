package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.dtu.message.DtuPassMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @see DtuFirstDeviceSnPackageHandler
 * @see LengthFieldBasedFrameForDtuDecoderServerComponent
 * @param <M>
 */
public class DtuPassMessageAware<M extends ServerMessage> extends DefaultDtuMessageAware<M>{

    /**
     * 对于dtu的注册包、心跳包和解码器分开的组件需要将报文交给解码器处理
     * @see DtuFirstDeviceSnPackageHandler#channelRead0(ChannelHandlerContext, ByteBuf)
     * @param equipCode 设备编号
     * @param message  未读数据的备份
     * @param msg 需要读取的报文
     * @return
     */
    @Override
    protected M customizeType(String equipCode, byte[] message, ByteBuf msg) {
        return (M) DtuPassMessage.getInstance();
    }
}
