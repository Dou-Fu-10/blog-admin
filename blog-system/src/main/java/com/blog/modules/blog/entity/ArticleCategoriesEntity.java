package com.blog.modules.blog.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ArticleCategories)表实体类
 *
 * @author IKUN
 * @since 2023-05-28 15:21:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_article_categories")
public class ArticleCategoriesEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 文章id
     */
    private Long aid;
    /**
     * 分类id
     */
    private Long cid;
    public ArticleCategoriesEntity(Long aid, Long cid) {
        this.aid = aid;
        this.cid = cid;
    }


}

