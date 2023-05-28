package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.entity.dto.CategoriesDto;
import com.blog.modules.blog.service.CategoriesService;
import jakarta.annotation.Resource;
import org.simpleframework.xml.core.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * (Categories)表控制层
 *
 * @author IKUN
 * @since 2023-05-27 20:01:06
 */
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    /**
     * 服务对象
     */
    @Resource
    private CategoriesService categoriesService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param categories 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<Object> selectAll(Page<CategoriesEntity> page, CategoriesEntity categories) {
        return new ResponseEntity<>(this.categoriesService.page(page, new QueryWrapper<>(categories)), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Serializable id) {
        return new ResponseEntity<>(this.categoriesService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param categories 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody @Validate CategoriesDto categories) {
        return new ResponseEntity<>(this.categoriesService.save(categories) ? "添加成功" : "添加失败", HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param categories 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Validate CategoriesDto categories) {
        return new ResponseEntity<>(this.categoriesService.updateById(categories) ? "修改成功" : "修改失败", HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam("idList") List<Long> idList) {
        return new ResponseEntity<>(this.categoriesService.removeByIds(idList), HttpStatus.OK);
    }
}

