package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.system.domain.RolesMenus;

import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface RolesMenusService extends IService<RolesMenus> {
    List<Long> queryMenuIdByRoleId(Long id);
    List<Long> queryRoleIdByMenuId(Long id);
    boolean removeByRoleId(Long id);
    boolean removeByMenuId(Long id);
}
