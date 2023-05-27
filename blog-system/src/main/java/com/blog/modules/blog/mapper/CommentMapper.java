package com.blog.modules.blog.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.CommentEntity;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-27 19:43:40
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentEntity> {

}

