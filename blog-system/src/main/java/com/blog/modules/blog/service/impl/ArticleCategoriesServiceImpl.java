package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.blog.modules.blog.mapper.ArticleCategoriesMapper;
import com.blog.modules.blog.service.ArticleCategoriesService;

/**
 * (ArticleCategories)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-28 15:21:29
 */
@Service("articleCategoriesService")
public class ArticleCategoriesServiceImpl extends ServiceImpl<ArticleCategoriesMapper, ArticleCategoriesEntity> implements ArticleCategoriesService {

}

