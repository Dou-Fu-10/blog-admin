package com.blog.modules.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.modules.blog.entity.InfoEntity;
import com.blog.modules.blog.service.InfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Info)表控制层
 *
 * @author IKUN
 * @since 2023-06-05 22:47:32
 */
@RestController
@RequestMapping("/api/info")
public class InfoController {
    /**
     * 服务对象
     */
    @Resource
    private InfoService infoService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param info 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<Object> selectAll(Page<InfoEntity> page, InfoEntity info) {
        return new ResponseEntity<>(this.infoService.page(page, new QueryWrapper<>(info)), HttpStatus.OK);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> selectOne(@PathVariable Serializable id) {
        return new ResponseEntity<>(this.infoService.getById(id), HttpStatus.OK);
    }

    /**
     * 新增数据
     *
     * @param info 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody InfoEntity info) {
        return new ResponseEntity<>(this.infoService.save(info), HttpStatus.OK);
    }

    /**
     * 修改数据
     *
     * @param info 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody InfoEntity info) {
        return new ResponseEntity<>(this.infoService.updateById(info), HttpStatus.OK);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Object> remove(@RequestBody Set<Long> idList) {
        return new ResponseEntity<>(this.infoService.removeByIds(idList.stream().filter(id -> String.valueOf(id).length() < 20 && String.valueOf(id).length() > 1).limit(10).collect(Collectors.toSet())) ? "删除成功" : "删除失败", HttpStatus.OK);
    }
}

