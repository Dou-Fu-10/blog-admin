package com.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
//import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.Accessors;
import com.blog.base.CommonModel;

import java.io.Serializable;

/**
* @author ty
* 
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tool_email_config")
public class EmailConfig extends CommonModel<EmailConfig> implements Serializable {
    private static final long serialVersionUID = 1L;


    // @ApiiModelProperty(value = "ID")
    @TableId(value = "config_id", type= IdType.ASSIGN_ID)
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
