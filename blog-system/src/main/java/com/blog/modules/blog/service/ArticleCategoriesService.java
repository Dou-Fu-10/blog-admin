package com.blog.modules.blog.service;

import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 修改数据
     *
     * @param categoriesId 主键结合
     * @return 修改结果
     */
    boolean updateCategories(Map<Long, Set<Long>> categoriesId);
}
