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
@JsonIgnoreProperties(value = {"handler"})
@TableName("sys_users_jobs")
// @ApiModel(value="UsersJobs对象", description="用户岗位关联")
public class UsersJobs implements Serializable {
    private static final long serialVersionUID = 1L;

    // @ApiModelProperty(value = "用户ID")
    @TableField(value = "user_id")
    private Long userId;

    // @ApiModelProperty(value = "岗位ID")
    @TableField(value = "job_id")
    private Long jobId;
}
