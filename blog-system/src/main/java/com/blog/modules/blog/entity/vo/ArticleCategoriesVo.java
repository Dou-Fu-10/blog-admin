package com.blog.modules.blog.entity.vo;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (ArticleCategories)表实体类
 *
 * @author IKUN
 * @since 2023-05-28 15:21:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCategoriesVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文章id
     */
    private Long aid;
    /**
     * 分类id
     */
    private Long cid;


}

