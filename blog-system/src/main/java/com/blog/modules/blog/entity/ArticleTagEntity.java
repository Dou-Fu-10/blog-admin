package com.blog.modules.blog.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ArticleTag)表实体类
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_article_tag")
public class ArticleTagEntity {
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
     * 标签id
     */
    private Long tid;

    public ArticleTagEntity(Long aid, Long tid) {
        this.aid = aid;
        this.tid = tid;
    }


}

