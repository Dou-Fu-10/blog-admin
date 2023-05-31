package com.blog.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.base.CommonEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@TableName("sys_job")
public class Job extends CommonEntity<Job> implements Serializable {
    private static final long serialVersionUID = 1L;

    // @ApiModelProperty(value = "ID")
    @TableId(value="job_id", type= IdType.AUTO)
    private Long id;

    // @ApiModelProperty(value = "岗位名称")
    @NotBlank
    private String name;

    // @ApiModelProperty(value = "岗位状态")
    @NotNull
    private Boolean enabled;

    // @ApiModelProperty(value = "排序")
    private Integer jobSort;

    public void copyFrom(Job source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
