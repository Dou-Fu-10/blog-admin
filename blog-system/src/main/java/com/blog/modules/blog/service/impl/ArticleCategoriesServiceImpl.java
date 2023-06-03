package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.mapper.ArticleCategoriesMapper;
import com.blog.modules.blog.service.ArticleCategoriesService;
import com.blog.modules.blog.service.CategoriesService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * (ArticleCategories)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-28 15:21:29
 */
@Service("articleCategoriesService")
public class ArticleCategoriesServiceImpl extends ServiceImpl<ArticleCategoriesMapper, ArticleCategoriesEntity> implements ArticleCategoriesService {

    @Resource
    @Lazy
    private CategoriesService categoriesService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCategories(Map<Long, Set<Long>> categoriesIdAndArticleList) {
        if (categoriesIdAndArticleList.isEmpty()) {
            throw new BadRequestException("请选择正确的类型");
        }
        Set<Map.Entry<Long, Set<Long>>> entries = categoriesIdAndArticleList.entrySet();
        for (Map.Entry<Long, Set<Long>> entry : entries) {
            // 获取分类id
            Long categories = entry.getKey();
            if (Objects.nonNull(categoriesService.getById(categories))) {
                // 文章id列表
                Set<Long> articleList = entry.getValue();
                LambdaQueryWrapper<ArticleCategoriesEntity> articleCategoriesEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                articleCategoriesEntityLambdaQueryWrapper.in(ArticleCategoriesEntity::getAid, articleList);
                // 删除 文章
                remove(articleCategoriesEntityLambdaQueryWrapper);
                ArrayList<ArticleCategoriesEntity> articleCategoriesEntityArrayList = new ArrayList<>();
                for (Long articleId : articleList) {
                    articleCategoriesEntityArrayList.add(new ArticleCategoriesEntity(articleId, categories));
                }
                return saveBatch(articleCategoriesEntityArrayList);
            }
        }
        return true;
    }

    @Override
    public List<CategoriesEntity> getArticleCategoriesByArticleId(Long articleId) {
        return getBaseMapper().getArticleCategoriesByArticleId(articleId);
    }

    @Override
    public boolean removeByArticleId(Long articleId) {
        return remove(Wrappers.<ArticleCategoriesEntity>lambdaQuery().eq(ArticleCategoriesEntity::getAid, articleId));
    }
}

