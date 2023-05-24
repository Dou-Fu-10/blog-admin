package com.blog.modules.quartz.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.quartz.domain.QuartzJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author ty
* 
*/
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

    List<QuartzJob> findByIsPauseIsFalse();
}
