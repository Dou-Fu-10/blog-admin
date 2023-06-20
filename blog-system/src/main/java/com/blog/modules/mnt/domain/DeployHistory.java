package com.blog.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
@TableName("mnt_deploy_history")
public class DeployHistory extends com.baomidou.mybatisplus.extension.activerecord.Model<DeployHistory> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * ID
     */
    @TableId(value = "history_id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 应用名称
     */
    @NotBlank
    private String appName;

    /**
     * 部署日期
     */
    @NotNull
    private Date deployDate;

    /**
     * 部署用户
     */
    @NotBlank
    private String deployUser;

    /**
     * 服务器IP
     */
    @NotBlank
    private String ip;

    /**
     * 部署编号
     */
    private Long deployId;

    public void copyFrom(DeployHistory source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
