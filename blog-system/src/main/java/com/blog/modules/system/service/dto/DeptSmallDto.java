package com.blog.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author ty
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
