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
@TableName("mnt_app")
public class App extends CommonEntity<App> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    // @ApiModelProperty(value = "ID")
    @TableId(value = "app_id", type = IdType.AUTO)
    private Long id;

    // @ApiModelProperty(value = "应用名称")
    private String name;

    // @ApiModelProperty(value = "上传目录")
    private String uploadPath;

    // @ApiModelProperty(value = "部署路径")
    private String deployPath;

    // @ApiModelProperty(value = "备份路径")
    private String backupPath;

    // @ApiModelProperty(value = "应用端口")
    private Integer port;

    // @ApiModelProperty(value = "启动脚本")
    private String startScript;

    // @ApiModelProperty(value = "部署脚本")
    private String deployScript;

    public void copyFrom(App source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
