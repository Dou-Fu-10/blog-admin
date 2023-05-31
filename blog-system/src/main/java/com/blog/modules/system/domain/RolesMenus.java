package com.blog.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色关联
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = { "handler" })
@TableName("sys_roles_menus")
// @ApiModel(value="RolesMenus对象", description="角色菜单关联")
public class RolesMenus implements Serializable{
    private static final long serialVersionUID = 1L;

    // @ApiModelProperty(value = "角色ID")
    @TableField(value = "role_id")
    private Long roleId;

    // @ApiModelProperty(value = "部门ID")
    @TableField(value = "menu_id")
    private Long menuId;
}
