package com.blog.modules.tools.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlipayConfigDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String appId;

    private String charset;

    private String format;

    private String gatewayUrl;

    private String notifyUrl;

    private String privateKey;

    private String publicKey;

    private String returnUrl;

    private String signType;

    private String sysServiceProviderId;
}
