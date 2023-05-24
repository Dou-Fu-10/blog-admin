
package com.blog.utils;

/**
 * @author ty
 * @apiNote 关于缓存的Key集合
 */
public interface CacheKey {
    
    /**
     * 用户
     */
    String USER_ID = "user::id:";
    
    /**
     * 数据
     */
    String DATA_USER = "data::user:";
    /**
     * 菜单
     */
    String MENU_ID = "menu::id:";
    String MENU_PID = "menu::pid:";
    String MENU_USER = "menu::user:";
    /**
     * 角色授权
     */
    String ROLE_AUTH = "role::auth:";
    /**
     * 角色信息
     */
    String ROLE_ID = "role::id:";
    
    /**
     * 部门
     */
    String DEPT_ID = "dept::id:";
    String DEPT_PID = "dept::pid:";
    /**
     * 岗位
     */
    String JOB_ID = "job::id:";
    /**
     * 数据字典
     */
    String DICT_NAME = "dict::name:";
    String DICTDEAIL_DICTID = "dictDetail::dictId:";
    String DICT_ID = "dict::id:";
    String DICTDEAIL_DICTNAME = "dictDetail::name:";
    
    
    
}
