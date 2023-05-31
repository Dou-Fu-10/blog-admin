package com.blog.modules.system.domain.vo;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class UserPassVo {

    private String oldPass;

    private String newPass;
}
