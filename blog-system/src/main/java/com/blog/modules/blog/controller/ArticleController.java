package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.ArticleEntity;
import com.blog.modules.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2023-05-27 19:42:18
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param article 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ArticleEntity> page, ArticleEntity article) {
        return success(this.articleService.page(page, new QueryWrapper<>(article)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.articleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param article 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ArticleEntity article) {
        return success(this.articleService.save(article));
    }

    /**
     * 修改数据
     *
     * @param article 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ArticleEntity article) {
        return success(this.articleService.updateById(article));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.articleService.removeByIds(idList));
    }
}

