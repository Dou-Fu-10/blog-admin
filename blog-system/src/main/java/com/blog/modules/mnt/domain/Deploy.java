package com.blog.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.base.CommonEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@TableName("mnt_deploy")
public class Deploy extends CommonEntity<Deploy> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    // @ApiModelProperty(value = "ID")
    @TableId(value = "deploy_id", type = IdType.AUTO)
    private Long id;

    // @ApiModelProperty(value = "应用编号")
    private Long appId;

    public void copyFrom(Deploy source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
