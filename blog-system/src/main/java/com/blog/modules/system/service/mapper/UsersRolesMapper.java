package com.blog.modules.system.service.mapper;

import com.blog.base.CommonMapper;
import com.blog.modules.system.domain.UsersRoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-25
*/
@Mapper
public interface UsersRolesMapper extends CommonMapper<UsersRoles> {

}
