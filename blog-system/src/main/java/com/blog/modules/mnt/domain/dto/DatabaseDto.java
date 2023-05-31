package com.blog.modules.mnt.domain.dto;

import com.blog.base.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
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
