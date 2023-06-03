package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import com.blog.utils.StringUtils;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(ArticleDto article) {

        ArticleEntity articleEntity = getByTitle(article.getTitle());
        if (Objects.nonNull(articleEntity) && !articleEntity.getId().equals(article.getId())) {
            throw new BadRequestException("文章标题已存在");
        }


        // 更新 是否成功
        if (updateById(ConvertUtil.convert(article, ArticleEntity.class))) {
            // 删除文章和标签的绑定
            articleTagService.removeByArticleId(article.getId());
            // 删除和 分类的绑定
            articleCategoriesService.removeByArticleId(article.getId());

            // 获取分类列表
            Set<Long> categoriesList = article.getCategoriesList();
            if (CollectionUtils.isNotEmpty(categoriesList)) {
                // 校验用户传输的分类id 只能储存已存在于数据的 分类
                Set<Long> categoriesId = categoriesService.listByIds(categoriesList).stream().map(CategoriesEntity::getId).collect(Collectors.toSet());
                // 将真实的分类id 于文章列表绑定 传入数据库
                if (!articleCategoriesService.saveBatch(categoriesId.stream().map(id -> new ArticleCategoriesEntity(article.getId(), id)).collect(Collectors.toSet()))) {
                    throw new BadRequestException("更新失败");
                }
            }

            // 获取标签列表
            Set<String> tagNameList = article.getTagList();
            // 校验用户传输的标签名 取出已存在于数据库的标签名
            List<TagEntity> tagByTagNameList = tagService.getTagByTagNameList(tagNameList);
            if (CollectionUtils.isNotEmpty(tagByTagNameList)) {
                // 转换成 标签名字
                Set<String> tagNameEntityList = tagByTagNameList.stream().map(TagEntity::getTagName).collect(Collectors.toSet());
                // 未进行校验的 标签名 和数据已存在的标签名去重
                tagNameList.removeAll(tagNameEntityList);
            }
            // 如果为空表示 将要添加的标签数据都 存在
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                // 将不存在于数据库的标签 保存到数据库
                Set<TagEntity> tagEntityCollect = tagNameList.stream().map(TagEntity::new).collect(Collectors.toSet());
                if (!tagService.saveBatch(tagEntityCollect)) {
                    throw new BadRequestException("更新失败");
                }
                // 将已存在数据库的和刚保存到数据库的进行合并
                if (!tagByTagNameList.addAll(tagEntityCollect)) {
                    throw new BadRequestException("更新失败");
                }
            }
            // 将真实的分类id 于文章列表绑定 传入数据库
            if (!articleTagService.saveBatch(tagByTagNameList.stream().map(tag -> new ArticleTagEntity(article.getId(), tag.getId())).collect(Collectors.toSet()))) {
                throw new BadRequestException("更新失败");
            }
            return true;
        }
        return false;
    }

    @Override
    public Page<ArticleVo> page(Page<ArticleEntity> page, ArticleDto articleDto) {
        // 获取要查询的分类
        Set<Long> categoriesList = articleDto.getCategoriesList();
        // 通过分类查询
        if (CollectionUtils.isNotEmpty(categoriesList)) {
            LambdaQueryWrapper<ArticleCategoriesEntity> articleCategoriesEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 查询和文章所绑定的分类
            articleCategoriesEntityLambdaQueryWrapper.in(ArticleCategoriesEntity::getCid, categoriesList);
            // 将 分类id 返回
            categoriesList = articleCategoriesService.list(articleCategoriesEntityLambdaQueryWrapper).stream().map(ArticleCategoriesEntity::getAid).collect(Collectors.toSet());
            // 查询数据为空 直接返回空
            if (StringUtils.isBlank(articleDto.getTitle()) && CollectionUtils.isEmpty(categoriesList)) {
                return new Page<>();
            }
        }
        ArticleEntity tempArticle = ConvertUtil.convert(articleDto, ArticleEntity.class);
        tempArticle.setTitle(null);
        // 使用查询条件进行查询
        LambdaQueryWrapper<ArticleEntity> articleEntityQueryWrapper = new LambdaQueryWrapper<>(tempArticle);
        // 判断 标题不为空即 添加 标题查询
        articleEntityQueryWrapper.like(StringUtils.isNoneBlank(articleDto.getTitle()), ArticleEntity::getTitle, articleDto.getTitle());
        // 分类id 部位为空即通过分类id 进行查询
        articleEntityQueryWrapper.in(CollectionUtils.isNotEmpty(categoriesList), ArticleEntity::getId, categoriesList);
        // 对文章进行分页
        Page<ArticleEntity> articleEntityPage = this.page(page, articleEntityQueryWrapper);
        // 查询数据为空 直接返回空
        if (CollectionUtils.isEmpty(articleEntityPage.getRecords())) {
            return new Page<>();
        }
        // 对数据进行转换 VO
        IPage<ArticleVo> convert = articleEntityPage.convert(article -> ConvertUtil.convert(article, ArticleVo.class));
        // 响应给用户的 数据
        List<ArticleVo> articleVoList = convert.getRecords();
        // 获取文章id
        Set<Long> articleIdList = articleVoList.stream().map(ArticleVo::getId).collect(Collectors.toSet());
        // 保证文章id 不为空
        if (CollectionUtils.isEmpty(articleIdList)) {
            return new Page<>();
        }
        // 获取文章id 所绑定的分类
        List<ArticleOrCategoriesVo> articleOrCategoriesVoList = getBaseMapper().getCategoriesByArticleId(articleIdList);
        // 保证文章 都有 绑定的 分类
        if (CollectionUtils.isNotEmpty(articleOrCategoriesVoList)) {
            // 对将要 响应给用户的 数据 进行循环  将分类添加到文章下面
            articleVoList = articleVoList.stream().peek(articleVo -> articleOrCategoriesVoList.forEach(articleOrCategoriesVo -> {
                if (articleOrCategoriesVo.getAid().equals(articleVo.getId())) {
                    articleVo.getCategoriesList().put(articleOrCategoriesVo.getCid(), articleOrCategoriesVo.getCname());
                }
            })).toList();
        }

        // 最终将要返回给
        convert.setRecords(articleVoList);
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
    public boolean removeByIds(Set<Long> idList) {
        // 将 长度大于 20 小于 1 的过滤掉 并且 一次最多删除 10 条
        Set<Long> collect = idList.stream().filter(id -> String.valueOf(id).length() < 20 && String.valueOf(id).length() > 1).limit(10).collect(Collectors.toSet());
        if (this.getBaseMapper().deleteBatchIds(collect) > 0) {
            // 将于文章和分类的绑定删除
            if (articleCategoriesService.remove(Wrappers.<ArticleCategoriesEntity>lambdaQuery().in(ArticleCategoriesEntity::getAid, collect))) {
                // 将 文章 和标签的绑定进行删除
                return articleTagService.remove(Wrappers.<ArticleTagEntity>lambdaQuery().in(ArticleTagEntity::getAid, collect));
            }
        }
        return false;
    }

    @Override
    public ArticleVo getById(Long id) {
        // 查询文章
        ArticleEntity articleEntity = getBaseMapper().selectById(id);
        if (Objects.isNull(articleEntity)) {
            throw new BadRequestException("没有此文章");
        }
        ArticleVo articleVo = ConvertUtil.convert(articleEntity, ArticleVo.class);
        // 获取 和文章绑定的分类
        List<CategoriesEntity> articleCategoriesByArticleId = articleCategoriesService.getArticleCategoriesByArticleId(articleEntity.getId());
        if (CollectionUtils.isNotEmpty(articleCategoriesByArticleId)) {
            Map<Long, String> categoriesMap = new HashMap<>(8);
            articleCategoriesByArticleId.forEach(categories -> categoriesMap.put(categories.getId(), categories.getName()));
            // 将分类添加到 前端显示中
            articleVo.setCategoriesList(categoriesMap);
        }
        // 获取 和文章绑定的分类
        List<TagEntity> articleTagEntityList = articleTagService.getArticleTagByArticleId(articleEntity.getId());
        if (CollectionUtils.isNotEmpty(articleTagEntityList)) {
            Map<Long, String> tagMap = new HashMap<>(8);
            articleTagEntityList.forEach(articleTag -> tagMap.put(articleTag.getId(), articleTag.getTagName()));
            articleVo.setTagList(tagMap);
        }

        return articleVo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(@NotNull ArticleDto article) {

        // TODO 判断填写的日期是否是 明天后的
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
        // 添加的标签数组
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

