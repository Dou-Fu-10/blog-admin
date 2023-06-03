package com.blog.modules.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.ArticleTagEntity;
import com.blog.modules.blog.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (ArticleTag)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTagEntity> {

    /**
     * 通过文章id 获取对应的标签
     *
     * @param articleId 文章id
     * @return 文章绑定标签
     */
    @Select("SELECT * FROM `blog_tag` WHERE id IN (SELECT `blog_article_tag`.`tid` FROM `blog_article_tag` WHERE `blog_article_tag`.`aid` = #{id})")
    List<TagEntity> getArticleTagByArticleId(@Param("id")Long articleId);
}

