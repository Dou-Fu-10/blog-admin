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
public class DatabaseDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String jdbcUrl;

    private String userName;

    private String pwd;
}
