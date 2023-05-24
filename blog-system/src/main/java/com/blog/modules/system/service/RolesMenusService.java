package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.system.domain.RolesMenus;
import java.util.List;

/**
* @author ty
*/
public interface RolesMenusService extends IService<RolesMenus> {
    List<Long> queryMenuIdByRoleId(Long id);
    List<Long> queryRoleIdByMenuId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByMenuId(Long id);
}
