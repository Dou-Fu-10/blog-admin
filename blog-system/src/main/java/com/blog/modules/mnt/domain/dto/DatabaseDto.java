package com.blog.modules.mnt.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.blog.base.CommonDto;

import java.io.Serial;
import java.io.Serializable;

/**
* @author ty
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DatabaseDto extends CommonDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String jdbcUrl;

    private String userName;

    private String pwd;
}
