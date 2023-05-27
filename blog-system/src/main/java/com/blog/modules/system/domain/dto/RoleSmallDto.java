package com.blog.modules.system.domain.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * @author ty
 */
@Data
public class RoleSmallDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
