package com.blog.modules.system.domain.dto;

import com.blog.annotation.Query;
import lombok.Data;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class DictDetailQueryParam {

    private String dictName;

    /**
     * 精确
     */
    @Query
    private Long detailId;

    /**
     * 精确
     */
    @Query
    private Long dictId;

    /**
     * 模糊
     */
    @Query(type = Query.Type.INNER_LIKE)
    private String label;
}
