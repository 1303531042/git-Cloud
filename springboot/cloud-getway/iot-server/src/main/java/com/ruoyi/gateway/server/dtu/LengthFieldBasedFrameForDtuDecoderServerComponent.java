package com.ruoyi.gateway.server.dtu;

import com.ruoyi.gateway.CoreConst;
import com.ruoyi.gateway.codec.adapter.LengthFieldBasedFrameMessageDecoderAdapter;
import com.ruoyi.gateway.config.ConnectProperties;
import com.ruoyi.gateway.server.ServerMessage;
import com.ruoyi.gateway.server.component.LengthFieldBasedFrameDecoderServerComponent;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.nio.ByteOrder;

/**
 * 使用长度字段解码器协议并且使用Dtu连网
 * @see DtuMessageDecoder
 * @see DtuFirstDeviceSnPackageHandler
 * @see LengthFieldBasedFrameDecoderServerComponent
 * @param <M>
 */
public abstract class LengthFieldBasedFrameForDtuDecoderServerComponent<M extends ServerMessage> extends LengthFieldBasedFrameDecoderServerComponent<M> implements DtuMessageDecoder<M> {

    private DtuMessageAware<M> dtuMessageAware;

    public LengthFieldBasedFrameForDtuDecoderServerComponent(ConnectProperties connectProperties, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(connectProperties, maxFrameLength, lengthFieldOffset, lengthFieldLength);
        this.setDtuMessageAware(new DtuPassMessageAware<>());
    }

    public LengthFieldBasedFrameForDtuDecoderServerComponent(ConnectProperties connectProperties, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(connectProperties, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.setDtuMessageAware(new DtuPassMessageAware<>());
    }

    public LengthFieldBasedFrameForDtuDecoderServerComponent(ConnectProperties connectProperties, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(connectProperties, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
        this.setDtuMessageAware(new DtuPassMessageAware<>());
    }

    public LengthFieldBasedFrameForDtuDecoderServerComponent(ConnectProperties connectProperties, ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(connectProperties, byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
        this.setDtuMessageAware(new DtuPassMessageAware<>());
    }

    @Override
    public ChannelInboundHandlerAdapter getMessageDecoder() {
        return new LengthFieldBasedFrameMessageDecoderAdapter(getByteOrder(), getMaxFrameLength(), getLengthFieldOffset()
                , getLengthFieldLength(), getLengthAdjustment(), getInitialBytesToStrip(), isFailFast());
    }

    @Override
    public void doInitChannel(ChannelPipeline p) {
        super.doInitChannel(p);
        p.addBefore(CoreConst.SERVER_DECODER_HANDLER, "DtuFirstDeviceSnPackageHandler", new DtuFirstDeviceSnPackageHandler(this));
    }

    public DtuMessageAware<M> getDtuMessageAwareDelegation() {
        return this.dtuMessageAware;
    }

    /**
     * @see DtuPassMessageAware
     * @param dtuMessageAware
     */
    public void setDtuMessageAware(DtuMessageAware<M> dtuMessageAware) {
        this.dtuMessageAware = dtuMessageAware;
        if(this.dtuMessageAware instanceof DefaultDtuMessageAware) {
            if(((DefaultDtuMessageAware<M>) this.dtuMessageAware).getDecoder() == null) {
                ((DefaultDtuMessageAware<M>) this.dtuMessageAware).setDecoder(this);
            }
        }
    }
}
