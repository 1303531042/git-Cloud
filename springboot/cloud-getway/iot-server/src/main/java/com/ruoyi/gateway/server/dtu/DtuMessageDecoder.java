package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.CoreConst;
import com.ruoyi.gateway.IotProtocolFactory;
import com.ruoyi.gateway.ProtocolException;
import com.ruoyi.gateway.codec.SocketMessageDecoder;
import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.server.IotSocketServer;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.dtu.message.DtuMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * DTU设备报文解码 <hr>
 *     第一包必须是dtu的注册包
 * @param <M>
 */
public interface DtuMessageDecoder<M extends ServerMessage> extends SocketMessageDecoder<ByteBuf>
        , IotProtocolFactory<M>, IotSocketServer, DtuMessageAware<M> {

    DtuMessageAware<M> getDtuMessageAwareDelegation();

    @Override
    default DtuMessageType messageType() {
        return getDtuMessageAwareDelegation().messageType();
    }

    @Override
    default String resolveEquipCode(byte[] equipMessage) {
        return getDtuMessageAwareDelegation().resolveEquipCode(equipMessage);
    }

    @Override
    default String resolveHeartbeat(byte[] message) {
        // https://gitee.com/iteaj/iot/issues/I5GN72
        return getDtuMessageAwareDelegation().resolveHeartbeat(message);
    }

    @Override
    default AbstractProtocol customize(M message, IotProtocolFactory<M> factory) {
        return getDtuMessageAwareDelegation().customize(message, factory);
    }

    @Override
    default M decodeBefore(String equipCode, byte[] message, ByteBuf msg) {
        return getDtuMessageAwareDelegation().decodeBefore(equipCode, message, msg);
    }

    @Override
    default M createMessage(byte[] message) {
        SocketMessage serverMessage = SocketMessageDecoder.super.createMessage(message);
        if(serverMessage instanceof DtuMessage) {
            return (M) serverMessage;
        } else {
            throw new ProtocolException("Dtu报文对象必须是["+DtuMessage.class.getSimpleName()+"]的子类");
        }
    }

    /**
     * 构建Dtu设备的第一个报文(上报设备编号)
     * @param ctx
     * @param buf
     * @throws Exception
     */
    default M buildDeviceSnMessage(ChannelHandlerContext ctx, ByteBuf buf) {
        byte[] message = new byte[buf.readableBytes()];
        buf.readBytes(message);
        String deviceSn = this.resolveEquipCode(message);

        // 由于第一包是字符串类型的设备编号 不执行读构建{@link SocketMessage#readBuild()}
        ServerMessage dtuMessage = this.createMessage(message);

        // 设置设备编号
        ((DtuMessage) dtuMessage).setEquipCode(deviceSn);

        // 设置DTU报文头
        ((DtuMessage) dtuMessage).setProtocolType(DtuCommonProtocolType.DEVICE_SN);
        return (M) dtuMessage;
    }

    @Override
    Class<M> getMessageClass();

    @Override
    default M doTcpDecode(ChannelHandlerContext ctx, ByteBuf in) {
        SocketMessage decode = SocketMessageDecoder.super.doTcpDecode(ctx, in);
        Object equipCode = ctx.channel().attr(CoreConst.EQUIP_CODE).get();

        // 设置Dtu报文的设备编号
        if(equipCode != null) {
            ((DtuMessage) decode).setEquipCode((String) equipCode);
        }

        return (M) decode;
    }

    default AbstractProtocol getProtocol(M message) {
        AbstractProtocol<M> protocol = customize(message, this);
        if(protocol != null) return protocol;

        // 获取自定义业务协议
        return doGetProtocol(message);
    }

    /**
     * 获取对应的协议
     * @param message
     * @return
     */
    AbstractProtocol doGetProtocol(M message);
}
