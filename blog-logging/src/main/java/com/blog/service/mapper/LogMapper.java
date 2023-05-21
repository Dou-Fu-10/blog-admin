package com.blog.service.mapper;

import com.blog.base.CommonMapper;
import com.blog.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface LogMapper extends CommonMapper<Log> {

}
