package com.blog.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modules.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    @Select("select d.dept_id as id, d.* from sys_dept d where dept_id = #{id}")
    Dept selectLink(Long id);

    @Select("SELECT d.dept_id as id, d.* FROM sys_dept d LEFT OUTER JOIN sys_roles_depts rd ON d.dept_id=rd.dept_id WHERE rd.role_id=#{roleId}")
    Set<Dept> selectByRoleId(Long roleId);

}
