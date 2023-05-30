package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.entity.dto.CategoriesDto;
import com.blog.modules.blog.mapper.CategoriesMapper;
import com.blog.modules.blog.service.ArticleCategoriesService;
import com.blog.modules.blog.service.CategoriesService;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * (Categories)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:23
 */
@Service("categoriesService")
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, CategoriesEntity> implements CategoriesService {

    @Resource
    private ArticleCategoriesService articleCategoriesService;

    @Override
    public CategoriesEntity getByCategoriesName(String categoriesName) {
        return lambdaQuery().eq(CategoriesEntity::getName, categoriesName).one();
    }

    @Override
    public boolean save(CategoriesDto categories) {
        Long pid = categories.getPid();
        if (pid != 0L) {
            if (Objects.isNull(getById(pid))) {
                throw new BadRequestException("父分类填写错误");
            }
        }
        // 校验名字是否被暂用
        if (Objects.nonNull(getByCategoriesName(categories.getName()))) {
            throw new BadRequestException("分类名已被占用");
        }
        return save(ConvertUtil.convert(categories, CategoriesEntity.class));
    }

    @Override
    public boolean updateById(CategoriesDto categories) {
        return updateById(ConvertUtil.convert(categories, CategoriesEntity.class));
    }
    @Override
    public boolean updateCategories(Map<Long, Set<Long>> categoriesId) {
        return articleCategoriesService.updateCategories(categoriesId);
    }

}

