package com.blog.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色关联
 *
 * @author ty
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = { "handler" })
@TableName("sys_users_roles")
// @ApiModel(value="UsersRoles对象", description="用户角色关联")
public class UsersRoles implements Serializable{
    private static final long serialVersionUID = 1L;

    // @ApiModelProperty(value = "用户ID")
    @TableField(value = "user_id")
    private Long userId;

    // @ApiModelProperty(value = "角色ID")
    @TableField(value = "role_id")
    private Long roleId;
}
