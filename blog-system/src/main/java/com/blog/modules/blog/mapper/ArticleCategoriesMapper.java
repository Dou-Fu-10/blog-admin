package com.blog.modules.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;

/**
 * (ArticleCategories)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-28 15:21:28
 */
@Mapper
public interface ArticleCategoriesMapper extends BaseMapper<ArticleCategoriesEntity> {

}

