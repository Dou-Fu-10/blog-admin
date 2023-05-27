package com.blog.modules.system.domain.dto;

import lombok.Data;
import com.blog.annotation.Query;

/**
* @author ty
*/
@Data
public class DictDetailQueryParam{

    private String dictName;

    /** 精确 */
    @Query
    private Long detailId;

    /** 精确 */
    @Query
    private Long dictId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String label;
}
