package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.blog.modules.blog.service.ArticleCategoriesService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * (ArticleCategories)表控制层
 *
 * @author IKUN
 * @since 2023-05-28 15:21:28
 */
@RestController
@RequestMapping("/api/articleCategories")
public class ArticleCategoriesController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleCategoriesService articleCategoriesService;

    /**
     * 分页查询所有数据
     *
     * @param page              分页对象
     * @param articleCategories 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<Object> selectAll(Page<ArticleCategoriesEntity> page, ArticleCategoriesEntity articleCategories) {
        return new ResponseEntity<>(this.articleCategoriesService.page(page, new QueryWrapper<>(articleCategories)), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Serializable id) {
        return new ResponseEntity<>(this.articleCategoriesService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param articleCategories 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody ArticleCategoriesEntity articleCategories) {
        return new ResponseEntity<>(this.articleCategoriesService.save(articleCategories), HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param articleCategories 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ArticleCategoriesEntity articleCategories) {
        return new ResponseEntity<>(this.articleCategoriesService.updateById(articleCategories), HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam("idList") List<Long> idList) {
        return new ResponseEntity<>(this.articleCategoriesService.removeByIds(idList), HttpStatus.OK);
    }
}

