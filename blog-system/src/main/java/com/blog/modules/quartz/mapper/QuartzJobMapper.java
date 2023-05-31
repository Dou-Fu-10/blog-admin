package com.blog.modules.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.quartz.domain.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

    List<QuartzJob> findByIsPauseIsFalse();
}
