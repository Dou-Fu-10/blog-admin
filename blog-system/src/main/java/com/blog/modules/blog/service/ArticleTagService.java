package com.blog.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.blog.entity.ArticleTagEntity;
import com.blog.modules.blog.entity.TagEntity;

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

    /**
     * 通过 文章id 列表 获取  文章和标签的绑定
     *
     * @param articleIdList 文章id列表
     * @return 文章和标签的绑定
     */
    List<ArticleTagEntity> getArticleTagByArticleIdList(Set<Long> articleIdList);

    /**
     * 通过文章id 获取对应的标签
     *
     * @param articleId 文章id
     * @return 文章绑定标签
     */
    List<TagEntity> getArticleTagByArticleId(Long articleId);

    /**
     * 通过文章id 删除 和文章关联的标签
     *
     * @param articleId 文章id
     * @return 是否成功
     */
    boolean removeByArticleId(Long articleId);

}
