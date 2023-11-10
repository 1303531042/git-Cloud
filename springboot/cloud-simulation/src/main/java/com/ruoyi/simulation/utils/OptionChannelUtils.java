package com.ruoyi.simulation.utils;

import io.netty.channel.Channel;

import java.util.Optional;

/**
 * @auther: KUN
 * @date: 2023/4/24
 * @description:
 */
public class OptionChannelUtils {

    /**
     * channel不存在 status 为1 未激活
     * channel存在 active 为 3 在线
     * channel存在 not active 为 4 离线
     * @param channelOp
     * @return
     */
    public static Integer checkChannelStatus(Optional<Channel> channelOp) {
        if (channelOp.isPresent()) {
            if (channelOp.map(Channel::isActive).get()) {
                return 3;
            }
            return 4;

        }
        return 1;
    }
}
