package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.NotebookEntity;
import com.blog.modules.blog.entity.dto.NotebookDto;
import com.blog.modules.blog.entity.vo.NotebookVo;
import com.blog.modules.blog.service.NotebookService;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> selectAll(Page<NotebookEntity> page, NotebookDto notebook) {
        Page<NotebookEntity> notebookEntityPage = this.notebookService.page(page, new QueryWrapper<>(ConvertUtil.convert(notebook, NotebookEntity.class)));
        return new ResponseEntity<>(notebookEntityPage.convert(notebookEntity -> ConvertUtil.convert(notebookEntity, NotebookVo.class)), HttpStatus.OK);
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
    public ResponseEntity<Object> insert(@RequestBody NotebookDto notebook) {
        return new ResponseEntity<>(this.notebookService.save(ConvertUtil.convert(notebook, NotebookEntity.class)) ? "添加成功" : "添加失败", HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param notebook 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody NotebookDto notebook) {
        return new ResponseEntity<>(this.notebookService.updateById(ConvertUtil.convert(notebook, NotebookEntity.class)) ? "修改成功" : "修改失败", HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Set<Long> idList) {
        return new ResponseEntity<>(this.notebookService.removeByIds(idList.stream().filter(id -> String.valueOf(id).length() < 20 && String.valueOf(id).length() > 1).limit(10).collect(Collectors.toSet())) ? "删除成功" : "删除失败", HttpStatus.OK);
    }
}

