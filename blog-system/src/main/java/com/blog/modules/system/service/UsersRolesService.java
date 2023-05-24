package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.system.domain.UsersRoles;
import java.util.List;

/**
* @author ty
*/
public interface UsersRolesService extends IService<UsersRoles> {
    List<Long> queryUserIdByRoleId(Long id);
    List<Long> queryRoleIdByUserId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByUserId(Long id);


}
