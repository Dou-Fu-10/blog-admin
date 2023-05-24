package com.blog.modules.mnt.service;

import com.blog.modules.mnt.domain.DeploysServers;


import java.util.List;

/**
* @author ty
* @date 2020-09-25
*/
public interface DeploysServersService extends IService<DeploysServers> {
    List<Long> queryDeployIdByServerId(Long id);
    List<Long> queryServerIdByDeployId(Long id);
    boolean removeByDeployId(Long id);
    boolean removeByServerId(Long id);
}
