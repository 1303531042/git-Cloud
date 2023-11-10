package com.ruoyi.gateway.protocol.socket;

import com.ruoyi.gateway.message.SocketMessage;
import com.ruoyi.gateway.protocol.AbstractProtocol;
import com.ruoyi.gateway.BusinessAction;

/**
 * create time: 2021/8/8
 *  用来声明此协议是一个socket协议 如：tcp, udp, smtp等
 * @author iteaj
 * @since 1.0
 */
public abstract class AbstractSocketProtocol<M extends SocketMessage>
        extends AbstractProtocol<M> implements BusinessAction {

}
