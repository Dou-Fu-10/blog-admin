package com.blog.modules.system.service;

import java.util.Map;

/**
 * @author ty
 */
public interface MonitorService {

    /**
    * 查询数据分页
    * @return Map<String,Object>
    */
    Map<String,Object> getServers();
}
