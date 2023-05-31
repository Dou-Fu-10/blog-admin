package com.blog.modules.system.service;

import com.blog.modules.system.domain.dto.UserDto;

import java.util.List;

/**
 * 数据权限服务类
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface DataService {

    /**
     * 获取数据权限
     * @param user /
     * @return /
     */
    List<Long> getDeptIds(UserDto user);
}
