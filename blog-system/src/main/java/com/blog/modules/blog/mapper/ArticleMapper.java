package com.blog.modules.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.ArticleEntity;

/**
 * (Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-27 19:42:19
 */
@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

}

