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
@TableName("tool_alipay_config")
public class AlipayConfig extends CommonModel<AlipayConfig> implements Serializable {
    private static final long serialVersionUID = 1L;


    // @ApiiModelProperty(value = "ID")
    @TableId(value = "config_id", type= IdType.ASSIGN_ID)
    private Long id;

    @NotBlank
    // @ApiiModelProperty(value = "应用ID")
    private String appId;

    // @ApiiModelProperty(value = "编码", hidden = true)
    private String charset= "utf-8";

    // @ApiiModelProperty(value = "类型 固定格式json")
    private String format = "JSON";;

    // @ApiiModelProperty(value = "支付宝开放安全地址", hidden = true)
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // @ApiiModelProperty(value = "异步通知地址")
    private String notifyUrl;

    @NotBlank
    // @ApiiModelProperty(value = "商户私钥")
    private String privateKey;

    @NotBlank
    // @ApiiModelProperty(value = "支付宝公钥")
    private String publicKey;

    @NotBlank
    // @ApiiModelProperty(value = "订单完成后返回的页面")
    private String returnUrl;

    // @ApiiModelProperty(value = "签名方式")
    private String signType = "RSA2";

    @NotBlank
    // @ApiiModelProperty(value = "商户号")
    private String sysServiceProviderId;

    public void copyFrom(AlipayConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
