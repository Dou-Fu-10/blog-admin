package com.blog.modules.mnt.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

/**
* @author ty
* 
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
