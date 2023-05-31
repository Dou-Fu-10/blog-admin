package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.ArticleTagEntity;
import com.blog.modules.blog.service.ArticleTagService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (ArticleTag)表控制层
 *
 * @author IKUN
 * @since 2023-05-31 21:25:42
 */
@RestController
@RequestMapping("/api/articleTag")
public class ArticleTagController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleTagService articleTagService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param articleTag 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<Object> selectAll(Page<ArticleTagEntity> page, ArticleTagEntity articleTag) {
        return new ResponseEntity<>(this.articleTagService.page(page, new QueryWrapper<>(articleTag)), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Serializable id) {
        return new ResponseEntity<>(this.articleTagService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param articleTag 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody ArticleTagEntity articleTag) {
        return new ResponseEntity<>(this.articleTagService.save(articleTag), HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param articleTag 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ArticleTagEntity articleTag) {
        return new ResponseEntity<>(this.articleTagService.updateById(articleTag), HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Set<Long> idList) {
        return new ResponseEntity<>(this.articleTagService.removeByIds(idList.stream().filter(id -> String.valueOf(id).length() < 20 && String.valueOf(id).length() > 1).limit(10).collect(Collectors.toSet())) ? "删除成功" : "删除失败", HttpStatus.OK);
    }
}

