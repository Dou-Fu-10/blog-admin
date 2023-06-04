package com.blog.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.blog.modules.blog.entity.CategoriesEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * (ArticleCategories)表服务接口
 *
 * @author IKUN
 * @since 2023-05-28 15:21:29
 */
public interface ArticleCategoriesService extends IService<ArticleCategoriesEntity> {
    /**
     * 修改文章类型  Map<类型id, Set<文章id>>
     *
     * @param categoriesIdAndArticleList Map<类型id, Set<文章id>
     * @return 修改结果
     */
    boolean updateCategories(Map<Long, Set<Long>> categoriesIdAndArticleList);

    /**
     * 文章id 获取 与其绑定的分类
     *
     * @param articleIdList 文章id
     * @return 文章和分类的绑定
     */
    List<ArticleCategoriesEntity> getArticleCategoriesByArticleIdList(Set<Long> articleIdList);

    /**
     * 文章id 获取 与其绑定的分类
     *
     * @param categoriesIdList 分类id
     * @return 文章和分类的绑定
     */
    List<ArticleCategoriesEntity> getArticleCategoriesByCategoriesIdList(Set<Long> categoriesIdList);

    /**
     * 通过
     *
     * @param articleId 文章id
     * @return 分类列表
     */
    List<CategoriesEntity> getArticleCategoriesByArticleId(Long articleId);

    /**
     * 通过文章id
     *
     * @param articleId 文章id
     * @return 是否成功
     */
    boolean removeByArticleId(Long articleId);
}
