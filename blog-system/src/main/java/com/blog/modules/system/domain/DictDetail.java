package com.blog.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.base.CommonEntity;
import com.blog.modules.system.domain.dto.DictSmallDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* @author ty
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_dict_detail")
public class DictDetail extends CommonEntity<DictDetail> implements Serializable {

   // @ApiModelProperty(value = "ID")
    @TableId(value = "detail_id", type= IdType.AUTO)
    private Long id;

    @NotNull
    private Long dictId;

   // @ApiModelProperty(value = "字典id")
    @NotNull
    @TableField(exist = false)
    private DictSmallDto dict;

   // @ApiModelProperty(value = "字典标签")
    @NotBlank
    private String label;

   // @ApiModelProperty(value = "字典值")
    @NotBlank
    private String value;

   // @ApiModelProperty(value = "排序")
    private Integer dictSort;

    public void copyFrom(DictDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
