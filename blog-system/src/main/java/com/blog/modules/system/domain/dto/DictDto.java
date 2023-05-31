package com.blog.modules.system.domain.dto;

import com.blog.base.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DictDto extends CommonDto implements Serializable {

    private Long id;

    //     private List<DictDetailDto> dictDetails;

    private String name;

    private String description;
}
