package com.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
//import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.Accessors;
import com.blog.base.CommonModel;

import java.io.Serializable;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tool_qiniu_config")
public class QiniuConfig extends CommonModel<QiniuConfig> implements Serializable {
    private static final long serialVersionUID = 1L;


   // @ApiodelProperty(value = "ID")
    @TableId(value = "config_id", type= IdType.ASSIGN_ID)
    private Long id;

    @NotBlank
   // @ApiodelProperty(value = "accessKey")
    private String accessKey;

    @NotBlank
   // @ApiodelProperty(value = "secretKey")
    private String secretKey;

    @NotBlank
   // @ApiodelProperty(value = "存储空间名称作为唯一的 Bucket 识别符")
    private String bucket;

    /**
     * Zone表示与机房的对应关系
     * 华东	Zone.zone0()
     * 华北	Zone.zone1()
     * 华南	Zone.zone2()
     * 北美	Zone.zoneNa0()
     * 东南亚	Zone.zoneAs0()
     */
    @NotBlank
   // @ApiodelProperty(value = "Zone表示与机房的对应关系")
    private String zone;

    @NotBlank
   // @ApiodelProperty(value = "外链域名，可自定义，需在七牛云绑定")
    private String host;

   // @ApiodelProperty(value = "空间类型：公开/私有")
    private String type = "公开";

    public void copyFrom(QiniuConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
