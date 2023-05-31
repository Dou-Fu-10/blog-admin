package com.blog.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.system.domain.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    @Select("select j.job_id as id, j.* from sys_job where job_id = #{id}")
    Job selectLink(Long id);
}
