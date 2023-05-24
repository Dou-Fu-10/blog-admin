package com.blog.modules.system.domain.vo;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 * @author ty
 */
@Data
public class UserPassVo {

    private String oldPass;

    private String newPass;
}
