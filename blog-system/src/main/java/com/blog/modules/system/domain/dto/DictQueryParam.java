package com.blog.modules.system.domain.dto;

import com.blog.annotation.Query;
import lombok.Data;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class DictQueryParam{

    @Query(blurry = "name,description")
    private String blurry;

    /** 精确 */
    @Query
    private Long dictId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
}
