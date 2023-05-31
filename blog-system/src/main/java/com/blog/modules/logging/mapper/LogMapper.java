package com.blog.modules.logging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.logging.domain.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

}
