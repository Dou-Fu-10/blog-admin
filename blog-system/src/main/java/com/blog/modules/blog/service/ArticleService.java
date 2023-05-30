package com.blog.modules.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.ArticleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.blog.entity.dto.ArticleDto;
import com.blog.modules.blog.entity.vo.ArticleVo;

import java.util.Map;
import java.util.Set;


/**
 * (Article)表服务接口
 *
 * @author IKUN
 * @since 2023-05-27 19:58:23
 */
public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 创建文章
     * @param article 文章
     * @return boolean
     */
    boolean save(ArticleDto article);

    /**
     * 通过文章标题查找文章
     * @param title 标题
     * @return 文章
     */
    ArticleEntity getByTitle(String title);
    /**
     * 修改数据
     * @param article 文章
     * @return boolean
     */
    boolean updateById(ArticleDto article);
    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param article 查询实体
     * @return 所有数据
     */
    Page<ArticleEntity> page(Page<ArticleEntity> page, ArticleDto article);

}
