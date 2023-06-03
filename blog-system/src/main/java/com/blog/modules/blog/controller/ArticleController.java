package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.base.ValidationDto;
import com.blog.modules.blog.entity.ArticleEntity;
import com.blog.modules.blog.entity.dto.ArticleDto;
import com.blog.modules.blog.service.ArticleService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Article)表控制层
 *
 * @author IKUN
 * @since 2023-05-27 20:01:06
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
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
    public ResponseEntity<Object> selectAll(Page<ArticleEntity> page, @Validated(ValidationDto.SelectPage.class) ArticleDto article) {
        return new ResponseEntity<>(this.articleService.page(page, article), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Long id) {
        return new ResponseEntity<>(this.articleService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param article 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody @Validated(ValidationDto.Insert.class) ArticleDto article) {
        return new ResponseEntity<>(this.articleService.save(article) ? "添加成功" : "添加失败", HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param article 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Validated(ValidationDto.Update.class) ArticleDto article) {
        return new ResponseEntity<>(this.articleService.updateById(article) ? "修改成功" : "修改失败", HttpStatus.OK);
    }

    /**
     * 修改文章是否发布 Map<发布, Set<文章id>>
     *
     * @param articleIdList 主键结合
     * @return 修改结果
     */
    @PutMapping("/updateArticleHide")
    public ResponseEntity<Object> updateArticleHide(@RequestBody @Validated @NotNull Map<@NotNull Boolean, Set<@NotNull Long>> articleIdList) {
        return new ResponseEntity<>(this.articleService.updateArticleTopOrHide(articleIdList, false) ? "添加成功" : "添加失败", HttpStatus.OK);
    }

    /**
     * 修改文章是否置顶 Map<是否自顶, Set<文章id>>
     *
     * @param articleIdList 主键结合
     * @return 修改结果
     */
    @PutMapping("/updateArticleTop")
    public ResponseEntity<Object> updateArticleTop(@RequestBody @Validated @NotNull Map<@NotNull Boolean, Set<@NotNull Long>> articleIdList) {
        return new ResponseEntity<>(this.articleService.updateArticleTopOrHide(articleIdList, true) ? "添加成功" : "添加失败", HttpStatus.OK);
    }


    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Set<Long> idList) {

        return new ResponseEntity<>(this.articleService.removeByIds(idList) ? "删除成功" : "删除失败", HttpStatus.OK);
    }
}

