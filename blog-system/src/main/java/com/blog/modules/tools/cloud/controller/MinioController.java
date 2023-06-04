package com.blog.modules.tools.cloud.controller;

import com.blog.annotation.rest.AnonymousGetMapping;
import com.blog.annotation.rest.AnonymousPostMapping;
import com.blog.modules.tools.cloud.service.MinioServer;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 微服务存储
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
@Slf4j
public class MinioController {

    @Resource
    private MinioServer minioServer;

    @AnonymousPostMapping("/images")
    public String uploadImages(MultipartFile file) {
        log.info("文件上传");
        return minioServer.uploadImages(file);
    }

    @AnonymousPostMapping("/file")
    public String file(MultipartFile file) {
        log.info("文件上传");
        return minioServer.upload(file);
    }

    @AnonymousGetMapping("/test")
    public String uploadImages() {
        return "minioServer.uploadImages(file)";
    }



}
