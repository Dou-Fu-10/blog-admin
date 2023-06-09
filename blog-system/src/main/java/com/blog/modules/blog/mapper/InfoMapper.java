package com.blog.modules.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.blog.entity.InfoEntity;

/**
 * (Info)表数据库访问层
 *
 * @author IKUN
 * @since 2023-06-05 22:47:33
 */
@Mapper
public interface InfoMapper extends BaseMapper<InfoEntity> {

}

