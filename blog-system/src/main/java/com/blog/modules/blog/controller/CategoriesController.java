package com.blog.modules.blog.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.service.CategoriesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Categories)表控制层
 *
 * @author makejava
 * @since 2023-05-27 19:43:36
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CategoriesService categoriesService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param categories 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<CategoriesEntity> page, CategoriesEntity categories) {
        return success(this.categoriesService.page(page, new QueryWrapper<>(categories)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.categoriesService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param categories 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody CategoriesEntity categories) {
        return success(this.categoriesService.save(categories));
    }

    /**
     * 修改数据
     *
     * @param categories 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody CategoriesEntity categories) {
        return success(this.categoriesService.updateById(categories));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.categoriesService.removeByIds(idList));
    }
}

