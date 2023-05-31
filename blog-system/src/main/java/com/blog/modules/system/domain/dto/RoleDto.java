package com.blog.modules.system.domain.dto;

import com.blog.base.CommonDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class RoleDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Set<MenuDto> menus;

    private Set<DeptDto> depts;

    private String name;

    private Integer level;

    private String description;

    private String dataScope;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
