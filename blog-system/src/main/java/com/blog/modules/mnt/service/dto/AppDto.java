package com.blog.modules.mnt.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.blog.base.CommonDto;

import java.io.Serializable;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String name;

    private String uploadPath;

    private String deployPath;

    private String backupPath;

    private Integer port;

    private String startScript;

    private String deployScript;

}
