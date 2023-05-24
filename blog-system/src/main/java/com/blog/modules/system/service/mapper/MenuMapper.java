package com.blog.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.system.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashSet;
import java.util.Set;

/**
* @author ty
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT m.menu_id as id, m.* FROM sys_roles_menus rm INNER JOIN sys_menu m ON rm.menu_id=m.menu_id WHERE rm.role_id=#{roleId}")
    Set<Menu> selectLink(Long roleId);

    @Select({"<script>SELECT m.menu_id as id, m.* FROM sys_roles_menus rm INNER JOIN sys_menu m ON rm.menu_id=m.menu_id WHERE rm.role_id IN"
            + "<foreach item='item' index='index' collection='roleIds' open='(' separator=',' close=')'> #{item} </foreach>"
            + "</script>"})
    Set<Menu> selectByRoleIds(@Param("roleIds") Set<Long> roleIds);

    @Select({"<script>SELECT m.menu_id as id, m.* FROM sys_menu m LEFT OUTER JOIN sys_roles_menus rm ON m.menu_id=rm.menu_id LEFT OUTER JOIN sys_role r ON rm.role_id=r.role_id WHERE r.role_id IN "
            + "<foreach item='item' index='index' collection='roleIds' open='(' separator=',' close=')'> #{item} </foreach>"
            + " AND m.type &lt;&gt; #{type} ORDER BY m.menu_sort ASC</script>"})
    LinkedHashSet<Menu> selectLinkRole(@Param("roleIds") Set<Long> roleIds, @Param("type") Long type);

}
