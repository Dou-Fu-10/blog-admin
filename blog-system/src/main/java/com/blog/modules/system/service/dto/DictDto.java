package com.blog.modules.system.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.blog.base.CommonDto;

import java.io.Serializable;

/**
* @author ty
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
