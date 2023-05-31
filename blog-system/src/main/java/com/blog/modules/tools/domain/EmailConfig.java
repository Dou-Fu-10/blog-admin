package com.blog.modules.tools.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tool_email_config")
public class EmailConfig extends com.baomidou.mybatisplus.extension.activerecord.Model<EmailConfig> implements Serializable {
    private static final long serialVersionUID = 1L;


    // @ApiiModelProperty(value = "ID")
    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    private Long id;

    @NotBlank
    // @ApiiModelProperty(value = "收件人")
    private String fromUser;

    @NotBlank
    // @ApiiModelProperty(value = "邮件服务器SMTP地址")
    private String host;

    @NotBlank
    // @ApiiModelProperty(value = "密码")
    private String pass;

    @NotBlank
    // @ApiiModelProperty(value = "端口")
    private String port;

    @NotBlank
    // @ApiiModelProperty(value = "发件者用户名")
    private String user;

    public void copyFrom(EmailConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
