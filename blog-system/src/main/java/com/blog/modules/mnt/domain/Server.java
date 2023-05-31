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
@TableName("mnt_server")
public class Server extends CommonEntity<Server> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    // @ApiModelProperty(value = "ID")
    @TableId(value="server_id", type= IdType.AUTO)
    private Long id;

    // @ApiModelProperty(value = "账号")
    private String account;

    // @ApiModelProperty(value = "IP地址")
    private String ip;

    // @ApiModelProperty(value = "名称")
    private String name;

    // @ApiModelProperty(value = "密码")
    private String password;

    // @ApiModelProperty(value = "端口")
    private Integer port;

    public void copyFrom(Server source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
