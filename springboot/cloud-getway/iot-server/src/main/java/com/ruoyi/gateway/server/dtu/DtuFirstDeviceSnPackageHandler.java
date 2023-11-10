package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.CoreConst;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.dtu.message.DtuMessage;
import com.ruoyi.gateway.server.dtu.message.DtuPassMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;

/**
 * dtu 上电第一包设备编号解码器<hr>
 *     1. 此handler要求dtu上报的第一包必须是设备编号
 *     2. 后面的报文如果内容和设备编号一致则作为心跳报文
 */
public class DtuFirstDeviceSnPackageHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private DtuMessageDecoder messageDecoder;

    public DtuFirstDeviceSnPackageHandler(DtuMessageDecoder messageDecoder) {
        super(false);
        this.messageDecoder = messageDecoder;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        Attribute attr = ctx.channel().attr(CoreConst.EQUIP_CODE);
        if (attr.get() == null) { // 设备编号不存在说明是DTU第一次上报设备编号
            buildDeviceSnMessage(ctx, buf);
        } else {
            byte[] message = new byte[buf.readableBytes()];

            // 解码异常 #I51ASQ
            buf.slice().readBytes(message);
            // 如果dtu除了上报设备编号的报文外, dtu自身还有其他的功能(心跳或者AT指令)
            // 注：需要开发者自己做粘包处理
            ServerMessage serverMessage = messageDecoder.decodeBefore((String) attr.get(), message, buf);

            if (serverMessage != null) {
                // 交由下一个解码器处理
                if(serverMessage instanceof DtuPassMessage) {
                    ctx.fireChannelRead(buf);
                } else {
                    //  交由下一个业务处理器处理
                    serverMessage.setChannelId(ctx.channel().id().asShortText());
                    ctx.fireChannelRead(serverMessage.readBuild());
                }
            } else {
                return; // 等待下一个报文的到来
            }
        }
    }

    /**
     * 构建Dtu设备的第一个报文(上报设备编号)
     * @param ctx
     * @param buf
     * @throws Exception
     */
    private void buildDeviceSnMessage(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        byte[] message = new byte[buf.readableBytes()];
        buf.readBytes(message).release(); // 读完直接释放
        String deviceSn = messageDecoder.resolveEquipCode(message);

        // 由于第一包是字符串类型的设备编号 不执行读构建{@link SocketMessage#readBuild()}
        ServerMessage dtuMessage = messageDecoder.createMessage(message);

        // 设置设备编号
        ((DtuMessage) dtuMessage).setEquipCode(deviceSn);

        // 设置DTU报文头
        ((DtuMessage) dtuMessage).setProtocolType(DtuCommonProtocolType.DEVICE_SN);
        ctx.fireChannelRead(dtuMessage.readBuild());
    }

}
