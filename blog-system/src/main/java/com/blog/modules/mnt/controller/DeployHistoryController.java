package com.blog.modules.mnt.controller;

import com.blog.modules.logging.annotation.Log;
import com.blog.modules.mnt.domain.dto.DeployHistoryQueryParam;
import com.blog.modules.mnt.service.DeployHistoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

/**
 * 运维：部署历史管理
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deployHistory")
public class DeployHistoryController {

    private final DeployHistoryService deployhistoryService;

    /**
     * 导出部署历史数据
     * @param response
     * @param criteria
     * @throws IOException
     */
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('deployHistory:list')")
    public void download(HttpServletResponse response, DeployHistoryQueryParam criteria) throws IOException {
        deployhistoryService.download(deployhistoryService.queryAll(criteria), response);
    }

    /**
     * 查询部署历史
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping
    @PreAuthorize("@el.check('deployHistory:list')")
    public ResponseEntity<Object> query(DeployHistoryQueryParam criteria, Pageable pageable) {
        return new ResponseEntity<>(deployhistoryService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    /**
     * 删除部署历史
     * @param ids
     * @return
     */
    @Log("删除DeployHistory")
    @DeleteMapping
    @PreAuthorize("@el.check('deployHistory:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> ids) {
        deployhistoryService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
