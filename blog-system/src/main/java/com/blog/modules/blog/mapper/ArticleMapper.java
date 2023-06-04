package com.blog.modules.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.ArticleEntity;
import com.blog.modules.blog.entity.vo.ArticleOrCategoriesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * (Article)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-27 19:58:23
 */
@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    /**
     * 通过文章id 列表 获取对应的 文章类型
     *
     * @param articleIdList 文章id 列表
     * @return 文章绑定的类型
     */
    @Select("<script>" +
            "SELECT \n" +
            "  `blog_article`.`id` AS aid,\n" +
            "  `blog_categories`.`id` AS cid,\n" +
            "  `blog_categories`.`name` AS cname \n" +
            "FROM\n" +
            "  `blog_article` \n" +
            "  JOIN `blog_article_categories` \n" +
            "    ON `blog_article`.`id` = `blog_article_categories`.`aid` \n" +
            "    AND `blog_article`.delete_flag = 0 \n" +
            "  JOIN `blog_categories` \n" +
            "    ON `blog_article_categories`.`cid` = `blog_categories`.`id` \n" +
            "WHERE `blog_categories`.`delete_flag` = 0 \n" +
            "  AND `blog_article`.`id` IN " +
            "<foreach collection='articleIdList' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<ArticleOrCategoriesVo> getCategoriesByArticleId(@Param("articleIdList")Set<Long> articleIdList);
}

