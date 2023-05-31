package com.blog.modules.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.ArticleTagEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (ArticleTag)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTagEntity> {

}

