package com.blog.modules.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Comment)表数据库访问层
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentEntity> {

}

