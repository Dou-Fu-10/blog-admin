package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.*;
import com.blog.modules.blog.entity.dto.ArticleDto;
import com.blog.modules.blog.entity.vo.ArticleOrCategoriesVo;
import com.blog.modules.blog.entity.vo.ArticleVo;
import com.blog.modules.blog.mapper.ArticleMapper;
import com.blog.modules.blog.service.*;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private ArticleTagService articleTagService;
    @Resource
    private TagService tagService;

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
    public Page<ArticleVo> page(Page<ArticleEntity> page, ArticleDto articleDto) {
        Set<Long> categoriesList = articleDto.getCategoriesList();
        if (CollectionUtils.isNotEmpty(categoriesList)) {
            articleDto.setTitle(null);
            LambdaQueryWrapper<ArticleCategoriesEntity> articleCategoriesEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleCategoriesEntityLambdaQueryWrapper.in(ArticleCategoriesEntity::getCid, categoriesList);
            categoriesList = articleCategoriesService.list(articleCategoriesEntityLambdaQueryWrapper).stream().map(ArticleCategoriesEntity::getAid).collect(Collectors.toSet());
        }
        ArticleEntity tempArticle = ConvertUtil.convert(articleDto, ArticleEntity.class);
        tempArticle.setTitle(null);
        LambdaQueryWrapper<ArticleEntity> articleEntityQueryWrapper = new LambdaQueryWrapper<>(tempArticle);
        articleEntityQueryWrapper.like(Objects.nonNull(articleDto.getTitle()), ArticleEntity::getTitle, articleDto.getTitle());
        articleEntityQueryWrapper.in(CollectionUtils.isNotEmpty(categoriesList), ArticleEntity::getId, categoriesList);
        IPage<ArticleVo> convert = this.page(page, articleEntityQueryWrapper).convert(article -> ConvertUtil.convert(article, ArticleVo.class));
        List<ArticleVo> articleVoList = convert.getRecords();
        Set<Long> articleIdList = articleVoList.stream().map(ArticleVo::getId).collect(Collectors.toSet());
        List<ArticleOrCategoriesVo> articleOrCategoriesVoList = getBaseMapper().getCategoriesByArticleId(articleIdList);
        List<ArticleVo> articleVos = articleVoList.stream().peek(articleVo -> articleOrCategoriesVoList.forEach(articleOrCategoriesVo -> {
            if (articleOrCategoriesVo.getAid().equals(articleVo.getId())) {
                articleVo.getCategoriesList().put(articleOrCategoriesVo.getCid(), articleOrCategoriesVo.getCname());
            }
        })).toList();

        convert.setRecords(articleVos);
        return (Page) convert;
    }

    @Override
    public boolean updateArticleTopOrHide(Map<Boolean, Set<Long>> articleIdList, boolean isTop) {
        if (articleIdList.isEmpty()) {
            throw new BadRequestException("请选择正确的类型");
        }
        Set<Map.Entry<Boolean, Set<Long>>> entries = articleIdList.entrySet();
        for (Map.Entry<Boolean, Set<Long>> entry : entries) {
            // 获取分类id
            Boolean top = entry.getKey();
            // 文章id列表
            Set<Long> articleList = entry.getValue();
            Set<ArticleEntity> collect = articleList.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()).stream().map(id -> {
                        ArticleEntity articleEntity = new ArticleEntity();
                        if (isTop) {
                            articleEntity.setTop(top);
                        } else {
                            articleEntity.setHide(top);
                        }
                        articleEntity.setId(id);
                        return articleEntity;
                    }).collect(Collectors.toSet());
            return updateBatchById(collect);
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(@NotNull ArticleDto article) {
        // 添加的标签数组

        // 获取 分类数据
        List<CategoriesEntity> categoriesEntitieList = categoriesService.getBaseMapper().selectBatchIds(article.getCategoriesList());

        // 判断分类是否为空
        if (CollectionUtils.isEmpty(categoriesEntitieList)) {
            throw new BadRequestException("分类填写有误");
        }
        // 判断数据库中文章标题是否存在
        if (Objects.nonNull(getByTitle(article.getTitle()))) {
            throw new BadRequestException("文章标题已存在");
        }
        Set<String> tagNameList = article.getTagList();
        Set<String> tempTagNameList = new HashSet<>(tagNameList);

        // 通过标签名列表，查出已存在数据库的 标签
        List<TagEntity> tagEntityList = tagService.getTagByTagNameList(tagNameList);
        if (CollectionUtils.isNotEmpty(tagEntityList)) {
            // 获取已存在数据库中的 标题名
            Set<String> repeat = tagEntityList.stream().map(TagEntity::getTagName).collect(Collectors.toSet());
            // 将已经在数据库的标签删除 ，留下没有被保存到数据库的
            repeat.forEach(tagNameList::remove);
        }

        // 判断 如果 tagNameList为空表示将要添加的标题数据库中都有
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 将去重后的 标签 存入数据库
            if (!tagService.saveTagByNameList(tagNameList)) {
                throw new BadRequestException("标签保存失败");
            }
        }
        ArticleEntity convert = ConvertUtil.convert(article, ArticleEntity.class);
        // 将文章保存到数据库中
        convert.insert();

        // 文章id 和 分类id 绑定在一起
        Set<ArticleCategoriesEntity> articleCategoriesEntitySet = categoriesEntitieList.stream().map(categoriesEntity -> new ArticleCategoriesEntity(convert.getId(), categoriesEntity.getId())).collect(Collectors.toSet());
        // 文章 和 分类 绑定在一起 保存到数据库中
        articleCategoriesService.saveBatch(articleCategoriesEntitySet);

        // 获取标签列表
        tagEntityList = tagService.getTagByTagNameList(tempTagNameList);
        // 将  文章 和 标签 绑定在一起
        Set<ArticleTagEntity> articleTagEntitySet = tagEntityList.stream().map(tagEntity -> new ArticleTagEntity(convert.getId(), tagEntity.getId())).collect(Collectors.toSet());

        return articleTagService.saveBatch(articleTagEntitySet);
    }
}

