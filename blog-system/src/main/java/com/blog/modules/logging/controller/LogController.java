package com.blog.modules.logging.controller;


import com.blog.modules.logging.annotation.Log;
import com.blog.modules.logging.domain.dto.LogQueryParam;
import com.blog.modules.logging.service.LogService;
import com.blog.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    @Log("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check()")
    public void download(HttpServletResponse response, LogQueryParam criteria) throws IOException {
        criteria.setLogType("INFO");
        logService.download(logService.queryAll(criteria), response);
    }

    @Log("导出错误数据")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@el.check()")
    public void downloadErrorLog(HttpServletResponse response, LogQueryParam criteria) throws IOException {
        criteria.setLogType("ERROR");
        logService.download(logService.queryAll(criteria), response);
    }

    @GetMapping
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> query(LogQueryParam criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> queryUserLog(LogQueryParam criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        criteria.setBlurry(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(logService.queryAllByUser(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLog(LogQueryParam criteria, Pageable pageable) {
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLogs(@PathVariable Long id) {
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllErrorLog() {
        logService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllInfoLog() {
        logService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
