package com.blog.modules.security.config.bean;

import lombok.Data;

/**
 * 登录验证码配置信息
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class LoginCode {
    
    /**
     * 是否启用验证码
     */
    private Boolean enabled = true;
    
    /**
     * 验证码配置
     */
    private LoginCodeEnum codeType;
    /**
     * 验证码有效期 分钟
     */
    private Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;

    public LoginCodeEnum getCodeType() {
        return codeType;
    }
}
