package com.blog.modules.system.domain.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
* @author ty
*/
@Data
public class DictDetailDto implements Serializable {

    private Long id;

    private DictSmallDto dict;

    private String label;

    private String value;

    private Integer dictSort;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;
}
