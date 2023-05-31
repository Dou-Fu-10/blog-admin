package com.blog.modules.system.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@NoArgsConstructor
public class DeptSmallDto implements Serializable {

    private Long id;
    private String name;

    public DeptSmallDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
