package com.blog.modules.system.controller;

import com.blog.exception.BadRequestException;
import com.blog.modules.logging.annotation.Log;
import com.blog.modules.system.domain.Dict;
import com.blog.modules.system.domain.dto.DictQueryParam;
import com.blog.modules.system.service.DictService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

/**
 * 系统：字典管理
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dict")
public class DictController {

    private static final String ENTITY_NAME = "dict";
    private final DictService dictService;

    /**
     * 导出字典数据
     */
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dict:list')")
    public void download(HttpServletResponse response, DictQueryParam criteria) throws IOException {
        dictService.download(dictService.queryAll(criteria), response);
    }

    /**
     * 查询字典
     */
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('dict:list')")
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(dictService.queryAll(new DictQueryParam()), HttpStatus.OK);
    }

    /**
     * 查询字典
     */
    @GetMapping
    @PreAuthorize("@el.check('dict:list')")
    public ResponseEntity<Object> query(DictQueryParam query, Pageable pageable) {
        return new ResponseEntity<>(dictService.queryAll(query, pageable), HttpStatus.OK);
    }

    /**
     * 新增字典
     */
    @Log("新增字典")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        dictService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 修改字典
     */
    @Log("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    public ResponseEntity<Object> update(@RequestBody Dict resources) {
        dictService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除字典
     */
    @Log("删除字典")
    @DeleteMapping
    @PreAuthorize("@el.check('dict:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        dictService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
