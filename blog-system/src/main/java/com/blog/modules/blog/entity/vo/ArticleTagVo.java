package com.blog.modules.blog.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ArticleTag)表实体类
 *
 * @author IKUN
 * @since 2023-05-31 21:25:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTagVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文章id
     */
    private Long aid;
    /**
     * 标签id
     */
    private Long tid;


}

