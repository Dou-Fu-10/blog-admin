package com.blog.modules.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.NotebookEntity;

/**
 * (Notebook)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@Mapper
public interface NotebookMapper extends BaseMapper<NotebookEntity> {

}

