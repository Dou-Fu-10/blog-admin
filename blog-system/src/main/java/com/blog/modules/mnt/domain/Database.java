package com.blog.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.base.CommonEntity;
import jakarta.validation.constraints.NotBlank;
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
@TableName("mnt_database")
public class Database extends CommonEntity<Database> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // @ApiModelProperty(value = "ID")
    @TableId(value = "db_id", type= IdType.ASSIGN_ID)
    private String id;

    // @ApiModelProperty(value = "名称")
    @NotBlank
    private String name;

    // @ApiModelProperty(value = "jdbc连接")
    @NotBlank
    private String jdbcUrl;

    // @ApiModelProperty(value = "账号")
    @NotBlank
    private String userName;

    // @ApiModelProperty(value = "密码")
    @NotBlank
    private String pwd;

    public void copyFrom(Database source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
