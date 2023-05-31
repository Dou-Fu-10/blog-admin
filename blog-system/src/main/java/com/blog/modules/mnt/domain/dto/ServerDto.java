package com.blog.modules.mnt.domain.dto;

import com.blog.base.CommonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
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
