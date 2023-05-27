package com.blog.modules.logging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.logging.domain.Log;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ty
*/
@Mapper
public interface LogMapper extends BaseMapper<Log> {

}
