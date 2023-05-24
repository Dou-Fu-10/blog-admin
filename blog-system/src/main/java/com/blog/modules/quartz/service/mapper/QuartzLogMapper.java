package com.blog.modules.quartz.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.quartz.domain.QuartzLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author ty
* 
*/
@Mapper
public interface QuartzLogMapper extends BaseMapper<QuartzLog> {

}
