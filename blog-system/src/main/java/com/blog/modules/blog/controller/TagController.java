package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.base.ValidationDto;
import com.blog.modules.blog.entity.TagEntity;
import com.blog.modules.blog.entity.dto.TagDto;
import com.blog.modules.blog.service.TagService;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Set;

/**
 * (Tag)表控制层
 *
 * @author IKUN
 * @since 2023-05-27 20:01:07
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param tagDto  查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<Object> selectAll(Page<TagEntity> page, TagDto tagDto) {
        return new ResponseEntity<>(this.tagService.page(page, tagDto), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Serializable id) {
        return new ResponseEntity<>(this.tagService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param tag 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody @Validated(ValidationDto.Insert.class) TagDto tag) {
        return new ResponseEntity<>(this.tagService.save(ConvertUtil.convert(tag, TagEntity.class)), HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param tag 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Validated(ValidationDto.Update.class)  TagDto tag) {
        return new ResponseEntity<>(this.tagService.updateById(ConvertUtil.convert(tag, TagEntity.class)), HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Set<Long> idList) {
        return new ResponseEntity<>(this.tagService.removeByIds(idList) ? "删除成功" : "删除失败", HttpStatus.OK);
    }
}

