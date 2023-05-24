package com.blog.modules.mnt.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.blog.base.CommonDto;

import java.io.Serializable;
import java.util.Objects;

/**
* @author ty
* 
*/
@Data
@NoArgsConstructor
public class ServerDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String account;

    private String ip;

    private String name;

    private String password;

    private Integer port;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerDto that = (ServerDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
