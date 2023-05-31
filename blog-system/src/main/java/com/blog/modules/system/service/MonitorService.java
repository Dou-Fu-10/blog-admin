package com.blog.modules.system.service;

import java.util.Map;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface MonitorService {

    /**
    * 查询数据分页
    * @return Map<String,Object>
    */
    Map<String,Object> getServers();
}
