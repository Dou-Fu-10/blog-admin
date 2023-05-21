package com.blog.modules.mnt.service;

import com.blog.modules.mnt.domain.DeploysServers;
import com.blog.base.CommonService;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface DeploysServersService extends CommonService<DeploysServers> {
    List<Long> queryDeployIdByServerId(Long id);
    List<Long> queryServerIdByDeployId(Long id);
    boolean removeByDeployId(Long id);
    boolean removeByServerId(Long id);
}
