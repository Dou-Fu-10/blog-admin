package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.ArticleEntity;
import com.blog.modules.blog.entity.dto.ArticleDto;
import com.blog.modules.blog.mapper.ArticleMapper;
import com.blog.modules.blog.service.ArticleService;
import com.blog.utils.ConvertUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (Article)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:23
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {

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
    public boolean save(@NotNull ArticleDto article) {
        // TODO 校验文章
        if (Objects.nonNull(getByTitle(article.getTitle()))) {
            throw new BadRequestException("文章标题已存在");
        }
        ArticleEntity convert = ConvertUtil.convert(article, ArticleEntity.class);
        return convert.insert();
    }
}

