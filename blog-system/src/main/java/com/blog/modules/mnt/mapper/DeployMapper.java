package com.blog.modules.mnt.mapper;

import com.blog.base.CommonMapper;
import com.blog.modules.mnt.domain.Deploy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface DeployMapper extends CommonMapper<Deploy> {

}
