package com.blog.modules.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.CategoriesEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Categories)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-28 13:22:04
 */
@Mapper
public interface CategoriesMapper extends BaseMapper<CategoriesEntity> {

}

