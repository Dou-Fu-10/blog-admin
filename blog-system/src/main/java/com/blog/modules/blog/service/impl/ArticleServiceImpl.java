package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.blog.modules.blog.entity.ArticleEntity;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.entity.dto.ArticleDto;
import com.blog.modules.blog.mapper.ArticleMapper;
import com.blog.modules.blog.service.ArticleCategoriesService;
import com.blog.modules.blog.service.ArticleService;
import com.blog.modules.blog.service.CategoriesService;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (Article)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:23
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {

    @Resource
    private CategoriesService categoriesService;
    @Resource
    private ArticleCategoriesService articleCategoriesService;

    @Override
    public ArticleEntity getByTitle(String title) {
        return lambdaQuery().eq(ArticleEntity::getTitle, title).one();
    }

    @Override
    public boolean updateById(ArticleDto article) {
        ArticleEntity articleEntity = getByTitle(article.getTitle());
        if (Objects.nonNull(articleEntity) && !articleEntity.getId().equals(article.getId())) {
            throw new BadRequestException("文章标题已存在");
        }
        return updateById(ConvertUtil.convert(article, ArticleEntity.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(@NotNull ArticleDto article) {
        // 获取所填写的分类id 是否存在数据库中
        List<CategoriesEntity> categoriesEntitieList = categoriesService.getBaseMapper().selectBatchIds(article.getCategoriesList());
        if (CollectionUtils.isEmpty(categoriesEntitieList)) {
            throw new BadRequestException("分类填写有误");
        }
        if (Objects.nonNull(getByTitle(article.getTitle()))) {
            throw new BadRequestException("文章标题已存在");
        }

        ArticleEntity convert = ConvertUtil.convert(article, ArticleEntity.class);
        convert.insert();
        // 将分类和文章进行绑定
        List<ArticleCategoriesEntity> articleCategoriesEntityList = new ArrayList<>();
        for (CategoriesEntity categoriesEntity : categoriesEntitieList) {
            // 文章id 和 分类id
            articleCategoriesEntityList.add(new ArticleCategoriesEntity(convert.getId(), categoriesEntity.getId()));
        }

        return articleCategoriesService.saveBatch(articleCategoriesEntityList);
    }
}

