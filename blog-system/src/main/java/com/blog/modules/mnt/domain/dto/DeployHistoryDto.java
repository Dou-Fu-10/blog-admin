package com.blog.modules.mnt.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeployHistoryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String appName;

    private Date deployDate;

    private String deployUser;

    private String ip;

    private Long deployId;
}
