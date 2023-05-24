package com.blog.modules.mnt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.mnt.domain.DeploysServers;

import java.util.List;

/**
* @author ty
*/
public interface DeploysServersService extends IService<DeploysServers> {
    List<Long> queryDeployIdByServerId(Long id);
    List<Long> queryServerIdByDeployId(Long id);
    boolean removeByDeployId(Long id);
    boolean removeByServerId(Long id);
}
