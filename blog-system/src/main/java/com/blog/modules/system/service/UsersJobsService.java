package com.blog.modules.system.service;

import com.blog.base.CommonService;
import com.blog.modules.system.domain.UsersJobs;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface UsersJobsService extends CommonService<UsersJobs> {
    List<Long> queryUserIdByJobId(Long id);
    List<Long> queryJobIdByUserId(Long id);
    boolean removeByUserId(Long id);
    boolean removeByJobId(Long id);
}
