package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.NotebookEntity;
import com.blog.modules.blog.service.NotebookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * (Notebook)表控制层
 *
 * @author IKUN
 * @since 2023-05-27 20:01:07
 */
@RestController
@RequestMapping("/api/notebook")
public class NotebookController {
    /**
     * 服务对象
     */
    @Resource
    private NotebookService notebookService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param notebook 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<Object> selectAll(Page<NotebookEntity> page, NotebookEntity notebook) {
        return new ResponseEntity<>(this.notebookService.page(page, new QueryWrapper<>(notebook)), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Serializable id) {
        return new ResponseEntity<>(this.notebookService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param notebook 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody NotebookEntity notebook) {
        return new ResponseEntity<>(this.notebookService.save(notebook), HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param notebook 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody NotebookEntity notebook) {
        return new ResponseEntity<>(this.notebookService.updateById(notebook), HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam("idList") List<Long> idList) {
        return new ResponseEntity<>(this.notebookService.removeByIds(idList), HttpStatus.OK);
    }
}

