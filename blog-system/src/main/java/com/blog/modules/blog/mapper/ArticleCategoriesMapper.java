package com.blog.modules.blog.mapper;

import com.blog.modules.blog.entity.CategoriesEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * (ArticleCategories)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-28 15:21:28
 */
@Mapper
public interface ArticleCategoriesMapper extends BaseMapper<ArticleCategoriesEntity> {

    /**
     * 通过文章id 查询出和他绑定的 分类
     * @param articleId 文章id
     * @return 文章
     */
    @Select("SELECT * FROM blog_categories WHERE id IN (SELECT `blog_article_categories`.`cid` FROM `blog_article_categories` WHERE `blog_article_categories`.`aid` = #{id})")
    List<CategoriesEntity> getArticleCategoriesByArticleId(@Param("id") Long articleId);

}

