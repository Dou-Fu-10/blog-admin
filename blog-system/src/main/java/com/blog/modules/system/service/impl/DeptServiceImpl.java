package com.blog.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.base.QueryHelpMybatisPlus;
import com.blog.commom.redis.service.RedisService;
import com.blog.exception.BadRequestException;
import com.blog.modules.system.domain.Dept;
import com.blog.modules.system.domain.User;
import com.blog.modules.system.domain.dto.DeptDto;
import com.blog.modules.system.domain.dto.DeptQueryParam;
import com.blog.modules.system.mapper.DeptMapper;
import com.blog.modules.system.mapper.RoleMapper;
import com.blog.modules.system.mapper.UserMapper;
import com.blog.modules.system.service.DeptService;
import com.blog.modules.system.service.RolesDeptsService;
import com.blog.utils.CacheKey;
import com.blog.utils.ConvertUtil;
import com.blog.utils.FileUtil;
import com.blog.utils.SecurityUtils;
import com.blog.utils.enums.DataScopeEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final DeptMapper deptMapper;
    private final RedisService redisService;
    private final RolesDeptsService rolesDeptsService;

    @Override
    public List<DeptDto> queryAll(DeptQueryParam query, Boolean isQuery) {
        if (isQuery) {
            query.setPidIsNull(true);
        }
        boolean notEmpty = StrUtil.isNotEmpty(query.getName()) || null != query.getPid()
                || CollectionUtils.isNotEmpty(query.getCreateTime());
        if (isQuery && notEmpty) {
            query.setPidIsNull(null);
        }

        return ConvertUtil.convertList(deptMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), DeptDto.class);
    }

    public List<DeptDto> queryAll2(DeptQueryParam query, Boolean isQuery) throws Exception {
        //Sort sort = new Sort(Sort.Direction.ASC, "deptSort");
        String dataScopeType = SecurityUtils.getDataScopeType();
        if (isQuery) {
            if (dataScopeType.equals(DataScopeEnum.ALL.getValue())) {
                query.setPidIsNull(true);
            }
            List<Field> fields = QueryHelpMybatisPlus.getAllFields(query.getClass(), new ArrayList<>());
            List<String> fieldNames = new ArrayList<String>() {{
                add("pidIsNull");
                add("enabled");
            }};
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(query);
                if (fieldNames.contains(field.getName())) {
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    query.setPidIsNull(null);
                    break;
                }
            }
        }
        QueryWrapper<Dept> wrapper = QueryHelpMybatisPlus.getPredicate(query);
        wrapper.lambda().orderByAsc(Dept::getDeptSort);
        List<DeptDto> list = ConvertUtil.convertList(deptMapper.selectList(wrapper), DeptDto.class);//deptMapper.toDto(deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), sort));

        // 如果为空，就代表为自定义权限或者本级权限，就需要去重，不理解可以注释掉，看查询结果
        if (StringUtils.isBlank(dataScopeType)) {
            return deduplication(list);
        }
        return list;
    }

    private List<DeptDto> deduplication(List<DeptDto> list) {
        List<DeptDto> deptDtos = new ArrayList<>();
        for (DeptDto deptDto : list) {
            boolean flag = true;
            for (DeptDto dto : list) {
                if (deptDto.getPid().equals(dto.getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                deptDtos.add(deptDto);
            }
        }
        return deptDtos;
    }

    @Override
    public List<DeptDto> queryAll() {
        return ConvertUtil.convertList(deptMapper.selectList(Wrappers.emptyWrapper()), DeptDto.class);
    }

    @Override
    public Dept getById(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public DeptDto findById(Long id) {
        return ConvertUtil.convert(getById(id), DeptDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Dept resources) {
        int ret = deptMapper.insert(resources);
        resources.setSubCount(0);
        if (resources.getPid() != null) {
            redisService.del(CacheKey.DEPT_PID + resources.getPid());
            updateSubCnt(resources.getPid());
            // 清理缓存
            delCaches(resources.getId(), null, resources.getPid());
        }
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Dept resources) {
        // 旧的部门
        Long pidOld = getById(resources.getId()).getPid();
        if (resources.getPid() != null && resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        int ret = deptMapper.updateById(resources);
        updateSubCnt(pidOld);
        updateSubCnt(resources.getPid());
        // 清理缓存
        delCaches(resources.getId(), pidOld, resources.getPid());

        return ret > 0;
    }

    private void updateSubCnt(Long deptId) {
        if (deptId == null) {
            return;
        }
        Dept parent = getById(deptId);
        LambdaQueryWrapper<Dept> deptLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deptLambdaQueryWrapper.eq(Dept::getPid, deptId);
        int count = (int) count(deptLambdaQueryWrapper);


        Dept dept = new Dept();
        dept.setSubCount(count);
        if (parent != null) {
            dept.setPid(parent.getPid());
        }
        lambdaUpdate().eq(Dept::getId, deptId)
                .update(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<Long> ids) {
        for (Long id : ids) {
            Dept dept = getById(id);
            // 清理缓存
            delCaches(id, dept.getPid(), null);
            updateSubCnt(dept.getPid());
            rolesDeptsService.removeByDeptId(id);
        }
        return deptMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        Set<Long> ids = new HashSet<>(1);
        ids.add(id);
        return removeByIds(ids);
    }
    @Override
    public List<Dept> findByPid(long pid) {
        return lambdaQuery().eq(Dept::getPid, pid).list();
    }

    @Override
    public Set<DeptDto> findByRoleId(Long roleId) {
        return ConvertUtil.convertSet(deptMapper.selectByRoleId(roleId), DeptDto.class);
    }

    @Override
    public List<DeptDto> getSuperior(DeptDto deptDto, List<Dept> depts) {
        if (deptDto.getPid() == null) {
            depts.addAll(lambdaQuery().isNull(Dept::getPid).list());
            return ConvertUtil.convertList(depts, DeptDto.class);
        }
        depts.addAll(lambdaQuery().eq(Dept::getPid, deptDto.getPid()).list());
        return getSuperior(ConvertUtil.convert(getById(deptDto.getPid()), DeptDto.class), depts);
    }

    @Override
    public Map<String, Object> buildTree(List<DeptDto> deptDtos) {
        Set<DeptDto> trees = new LinkedHashSet<>();
        Set<DeptDto> depts = new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDto::getName).collect(Collectors.toList());
        boolean isChild;
        for (DeptDto deptDTO : deptDtos) {
            isChild = false;
            if (deptDTO.getPid() == null) {
                trees.add(deptDTO);
            }
            for (DeptDto it : deptDtos) {
                if (it.getPid() != null && it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(deptDTO);
            } else {
                Dept dept = null;
                if (null != deptDTO.getPid()) {
                    dept = this.getById(deptDTO.getPid());
                }
                if (null != dept && !deptNames.contains(dept.getName())) {
                    depts.add(deptDTO);
                }
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("totalElements", deptDtos.size());
        map.put("content", CollectionUtils.isEmpty(trees) ? deptDtos : trees);
        return map;
    }

    @Override
    public Set<Long> getDeleteDepts(List<Dept> deptList, Set<Long> deptIds) {
        for (Dept dept : deptList) {
            deptIds.add(dept.getId());
            List<Dept> depts = findByPid(dept.getId());
            if (depts != null && depts.size() != 0) {
                getDeleteDepts(depts, deptIds);
            }
        }
        return deptIds;
    }

    @Override
    public List<Long> getDeptChildren(Long deptId, List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
            if (dept != null && dept.getEnabled()) {
                List<Dept> depts = lambdaQuery().eq(Dept::getPid, dept.getId()).list();
                if (depts.size() != 0) {
                    list.addAll(getDeptChildren(dept.getId(), depts));
                }
                list.add(dept.getId());
            }
        });
        return list;
    }

    @Override
    public void verification(Set<Long> deptIds) {
        if (userMapper.countByDepts(deptIds) > 0) {
            throw new BadRequestException("所选部门存在用户关联，请解除后再试！");
        }
        if (roleMapper.countByDepts(deptIds) > 0) {
            throw new BadRequestException("所选部门存在角色关联，请解除后再试！");
        }
    }

    @Override
    public void download(List<DeptDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeptDto dept : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("上级部门", dept.getPid());
            map.put("子部门数目", dept.getSubCount());
            map.put("名称", dept.getName());
            map.put("排序", dept.getDeptSort());
            map.put("状态", dept.getEnabled());
            map.put("创建者", dept.getCreateBy());
            map.put("更新者", dept.getUpdateBy());
            map.put("创建日期", dept.getCreateTime());
            map.put("更新时间", dept.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, Long pidOld, Long pidNew) {
        List<User> users = userMapper.findByRoleDeptId(id);
        // 删除数据权限
        redisService.delByKeys(CacheKey.DATA_USER, users.stream().map(User::getId).collect(Collectors.toSet()));
        redisService.del(CacheKey.DEPT_ID + id);
        redisService.del(CacheKey.DEPT_PID + (pidOld == null ? 0 : pidOld));
        redisService.del(CacheKey.DEPT_PID + (pidNew == null ? 0 : pidNew));
    }

}
