package com.blog.service.mapper;

import com.blog.domain.EmailConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author ty
* 
*/
@Mapper
public interface EmailConfigMapper extends BaseMapper<EmailConfig> {

}
