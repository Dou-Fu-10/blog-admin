package com.blog.modules.tools.domain.dto;

import com.blog.base.CommonDto;
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
public class LocalStorageDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String realName;

    private String name;

    private String suffix;

    private String path;

    private String type;

    private String size;
}
