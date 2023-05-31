package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.system.domain.RolesDepts;

import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface RolesDeptsService extends IService<RolesDepts> {

    List<Long> queryDeptIdByRoleId(Long id);
    List<Long> queryRoleIdByDeptId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByDeptId(Long id);
}
