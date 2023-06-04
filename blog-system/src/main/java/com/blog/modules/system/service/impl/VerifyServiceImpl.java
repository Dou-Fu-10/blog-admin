package com.blog.modules.system.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.blog.commom.redis.service.RedisService;
import com.blog.exception.BadRequestException;
import com.blog.modules.system.service.VerifyService;
import com.blog.modules.tools.domain.vo.EmailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    private final RedisService redisService;
    @Value("${code.expiration}")
    private Long expiration;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailVo sendEmail(String email, String key) {
        EmailVo emailVo;
        String content;
        String redisKey = key + email;
        // 如果不存在有效的验证码，就创建一个新的
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email/email.ftl");
        Object oldCode = redisService.get(redisKey);
        if (oldCode == null) {
            String code = RandomUtil.randomNumbers(6);
            // 存入缓存
            if (!redisService.set(redisKey, code, expiration)) {
                throw new BadRequestException("服务异常，请联系网站负责人");
            }
            content = template.render(Dict.create().set("code", code));
            emailVo = new EmailVo(Collections.singletonList(email), "EL-ADMIN后台管理系统", content);
            // 存在就再次发送原来的验证码
        } else {
            content = template.render(Dict.create().set("code", oldCode));
            emailVo = new EmailVo(Collections.singletonList(email), "EL-ADMIN后台管理系统", content);
        }
        return emailVo;
    }

    @Override
    public void validated(String key, String code) {
        Object value = redisService.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new BadRequestException("无效验证码");
        } else {
            redisService.del(key);
        }
    }
}
