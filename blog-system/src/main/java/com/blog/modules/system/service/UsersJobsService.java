package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.system.domain.UsersJobs;

import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface UsersJobsService extends IService<UsersJobs> {
    List<Long> queryUserIdByJobId(Long id);

    List<Long> queryJobIdByUserId(Long id);

    boolean removeByUserId(Long id);

    boolean removeByJobId(Long id);
}
