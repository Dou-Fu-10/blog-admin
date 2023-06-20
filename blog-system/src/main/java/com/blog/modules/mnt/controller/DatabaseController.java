package com.blog.modules.mnt.controller;

import com.blog.exception.BadRequestException;
import com.blog.modules.logging.annotation.Log;
import com.blog.modules.mnt.domain.Database;
import com.blog.modules.mnt.domain.dto.DatabaseDto;
import com.blog.modules.mnt.domain.dto.DatabaseQueryParam;
import com.blog.modules.mnt.service.DatabaseService;
import com.blog.modules.mnt.util.SqlUtils;
import com.blog.utils.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * 运维：数据库管理
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/database")
public class DatabaseController {

    private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
    private final DatabaseService databaseService;

    /**
     * 导出数据库数据
     * @param response
     * @param criteria
     * @throws IOException
     */
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('database:list')")
    public void download(HttpServletResponse response, DatabaseQueryParam criteria) throws IOException {
        databaseService.download(databaseService.queryAll(criteria), response);
    }

    /**
     * 查询数据库
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping
    @PreAuthorize("@el.check('database:list')")
    public ResponseEntity<Object> query(DatabaseQueryParam criteria, Pageable pageable) {
        return new ResponseEntity<>(databaseService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    /**
     * 新增数据库
     * @param resources
     * @return
     */
    @Log("新增数据库")
    @PostMapping
    @PreAuthorize("@el.check('database:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Database resources) {
        databaseService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 修改数据库
     * @param resources
     * @return
     */
    @Log("修改数据库")
    @PutMapping
    @PreAuthorize("@el.check('database:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Database resources) {
        databaseService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除数据库
     * @param ids
     * @return
     */
    @Log("删除数据库")
    @DeleteMapping
    @PreAuthorize("@el.check('database:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> ids) {
        databaseService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 测试数据库链接
     * @param resources
     * @return
     */
    @Log("测试数据库链接")
    @PostMapping("/testConnect")
    @PreAuthorize("@el.check('database:testConnect')")
    public ResponseEntity<Object> testConnect(@Validated @RequestBody Database resources) {
        return new ResponseEntity<>(databaseService.testConnection(resources), HttpStatus.CREATED);
    }

    /**
     * 执行SQL脚本
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @Log("执行SQL脚本")
    @PostMapping(value = "/upload")
    @PreAuthorize("@el.check('database:add')")
    public ResponseEntity<Object> upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        DatabaseDto database = databaseService.findById(id);
        String fileName;
        if (database != null) {
            fileName = file.getOriginalFilename();
            File executeFile = new File(fileSavePath + fileName);
            FileUtil.del(executeFile);
            file.transferTo(executeFile);
            String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUserName(), database.getPwd(), executeFile);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            throw new BadRequestException("Database not exist");
        }
    }
}
