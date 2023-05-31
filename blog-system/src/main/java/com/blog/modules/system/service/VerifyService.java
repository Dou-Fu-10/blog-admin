package com.blog.modules.system.service;

import com.blog.modules.tools.domain.vo.EmailVo;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface VerifyService {

    /**
     * 发送验证码
     * @param email /
     * @param key /
     * @return /
     */
    EmailVo sendEmail(String email, String key);


    /**
     * 验证
     * @param code /
     * @param key /
     */
    void validated(String key, String code);
}
