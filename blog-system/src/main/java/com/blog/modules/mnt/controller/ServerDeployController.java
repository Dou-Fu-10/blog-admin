package com.blog.modules.mnt.controller;

import com.blog.modules.logging.annotation.Log;
import com.blog.modules.mnt.domain.Server;
import com.blog.modules.mnt.domain.dto.ServerQueryParam;
import com.blog.modules.mnt.service.ServerService;
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
 * 运维：服务器管理
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/serverDeploy")
public class ServerDeployController {

    private final ServerService serverDeployService;

    /**
     * 导出服务器数据
     * @param response
     * @param criteria
     * @throws IOException
     */
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('serverDeploy:list')")
    public void download(HttpServletResponse response, ServerQueryParam criteria) throws IOException {
        serverDeployService.download(serverDeployService.queryAll(criteria), response);
    }

    /**
     * 查询服务器
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping
    @PreAuthorize("@el.check('serverDeploy:list')")
    public ResponseEntity<Object> query(ServerQueryParam criteria, Pageable pageable) {
        return new ResponseEntity<>(serverDeployService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    /**
     * 新增服务器
     * @param resources
     * @return
     */
    @Log("新增服务器")
    @PostMapping
    @PreAuthorize("@el.check('serverDeploy:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Server resources) {
        serverDeployService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 修改服务器
     * @param resources
     * @return
     */
    @Log("修改服务器")
    @PutMapping
    @PreAuthorize("@el.check('serverDeploy:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Server resources) {
        serverDeployService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除Server
     * @param ids
     * @return
     */
    @Log("删除服务器")
    @DeleteMapping
    @PreAuthorize("@el.check('serverDeploy:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        serverDeployService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 测试连接服务器
     * @param resources
     * @return
     */
    @Log("测试连接服务器")
    @PostMapping("/testConnect")
    @PreAuthorize("@el.check('serverDeploy:add')")
    public ResponseEntity<Object> testConnect(@Validated @RequestBody Server resources) {
        return new ResponseEntity<>(serverDeployService.testConnect(resources), HttpStatus.CREATED);
    }
}
