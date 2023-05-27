package com.blog.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.system.domain.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author ty
*/
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    @Select("select j.job_id as id, j.* from sys_job where job_id = #{id}")
    Job selectLink(Long id);
}
