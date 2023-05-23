package com.blog.modules.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.Accessors;
import com.blog.base.CommonEntity;

import java.io.Serial;
import java.io.Serializable;

/**
* @author ty
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
