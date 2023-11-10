package com.ruoyi.common.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.stereotype.Component;


@Component
public class CheckUtils {
    private static final RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public static void validateCaptcha(String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        //生成的验证码为空
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        //验证码不正确
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }

}
