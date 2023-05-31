package com.blog.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.blog.entity.ArticleTagEntity;

import java.util.List;
import java.util.Set;


/**
 * (ArticleTag)表服务接口
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface ArticleTagService extends IService<ArticleTagEntity> {

    /**
     * 通过 标签id 列表 获取  文章和标签的绑定
     *
     * @param tagIdList 标签列表
     * @return 文章和标签的绑定
     */
    List<ArticleTagEntity> getArticleTagByTagIdList(Set<Long> tagIdList);
}
