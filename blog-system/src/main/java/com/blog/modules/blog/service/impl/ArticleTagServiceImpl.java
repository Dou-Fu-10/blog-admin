package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.modules.blog.entity.ArticleTagEntity;
import com.blog.modules.blog.entity.TagEntity;
import com.blog.modules.blog.mapper.ArticleTagMapper;
import com.blog.modules.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * (ArticleTag)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-31 21:25:44
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTagEntity> implements ArticleTagService {

    @Override
    public List<ArticleTagEntity> getArticleTagByTagIdList(Set<Long> tagIdList) {
        return list(Wrappers.<ArticleTagEntity>lambdaQuery().in(ArticleTagEntity::getTid, tagIdList));
    }

    @Override
    public List<ArticleTagEntity> getArticleTagByArticleIdList(Set<Long> articleIdList) {
        return list(Wrappers.<ArticleTagEntity>lambdaQuery().in(ArticleTagEntity::getAid, articleIdList));
    }

    @Override
    public List<TagEntity> getArticleTagByArticleId(Long articleId) {
        return getBaseMapper().getArticleTagByArticleId(articleId);
    }

    @Override
    public boolean removeByArticleId(Long articleId) {
        return remove(Wrappers.<ArticleTagEntity>lambdaQuery().eq(ArticleTagEntity::getAid, articleId));
    }

}

