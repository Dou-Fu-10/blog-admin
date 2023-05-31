package com.blog.modules.tools.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio 文件存储
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 **/
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private int partSize;
    
    private String bucketName;
    private String rootPath;
    private String domain;
    
}
