package com.blog.modules.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用与服务器部署关联
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = {"handler"})
@TableName("mnt_deploy_server")
public class DeploysServers extends com.baomidou.mybatisplus.extension.activerecord.Model<DeploysServers> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部署ID
     */
    @TableField(value = "deploy_id")
    private Long deployId;

    /**
     * 服务器ID
     */
    @TableField(value = "server_id")
    private Long serverId;

}
