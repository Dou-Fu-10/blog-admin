package com.blog.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.system.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT r.role_id as id, r.* FROM sys_role r LEFT OUTER JOIN sys_users_roles ur ON r.role_id=ur.role_id LEFT OUTER JOIN sys_user u ON ur.user_id=u.user_id WHERE u.user_id=#{userId}")
    Set<Role> selectLink(Long userId);

    /**
     * 根据用户ID查询
     *
     * @param id 用户ID
     * @return /
     */
    @Select("SELECT r.* FROM sys_role r, sys_users_roles u WHERE " + "r.role_id = u.role_id AND u.user_id = #{id}")
    Set<Role> findByUserId(@Param("id") Long id);

    /**
     * 根据部门查询
     *
     * @param deptIds /
     * @return /
     */
    /*@Select("<script>select count(1) from sys_role r, sys_roles_depts d where "
            + "r.role_id = d.role_id and d.dept_id in "
            + "<foreach item='item' index='index' collection='deptIds' open='(' separator=',' close=')'> #{item} </foreach>"
            + "</script>")*/
    int countByDepts(@Param("deptIds") Set<Long> deptIds);

    /**
     * 根据菜单Id查询
     * @param menuIds /
     * @return /
     */
    /*@Select("SELECT r.* FROM sys_role r, sys_roles_menus m WHERE " +
            "r.role_id = m.role_id AND m.menu_id in #{menuIds}")*/
    List<Role> findInMenuId(@Param("menuIds") List<Long> menuIds);

}
