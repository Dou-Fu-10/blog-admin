package com.blog.modules.tools.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.base.CommonEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* @author ty
* 
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tool_local_storage")
public class LocalStorage extends CommonEntity<LocalStorage> implements Serializable {
    private static final long serialVersionUID = 1L;


   // @ApiModelProperty(value = "ID")
    @TableId(value = "storage_id", type= IdType.AUTO)
    private Long id;

   // @ApiModelProperty(value = "文件真实的名称")
    private String realName;

   // @ApiModelProperty(value = "文件名")
    private String name;

   // @ApiModelProperty(value = "后缀")
    private String suffix;

   // @ApiModelProperty(value = "路径")
    private String path;

   // @ApiModelProperty(value = "类型")
    private String type;

   // @ApiModelProperty(value = "大小")
    private String size;

    public LocalStorage(String realName,String name, String suffix, String path, String type, String size) {
        this.realName = realName;
        this.name = name;
        this.suffix = suffix;
        this.path = path;
        this.type = type;
        this.size = size;
    }
    public void copyFrom(LocalStorage source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
