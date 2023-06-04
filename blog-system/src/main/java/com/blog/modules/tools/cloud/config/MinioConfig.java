package com.blog.modules.tools.cloud.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Minio 文件存储
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    /**
     * 文件大小限制
     */
    private Long maxSize;

    /**
     * 头像大小限制
     */
    private Long imagesMaxSize;


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
