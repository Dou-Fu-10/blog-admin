package com.blog.modules.blog.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.CategoriesEntity;

/**
 * (Categories)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-27 19:43:37
 */
@Mapper
public interface CategoriesMapper extends BaseMapper<CategoriesEntity> {

}

