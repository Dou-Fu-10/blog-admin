package com.blog;

import com.blog.annotation.rest.AnonymousGetMapping;
import com.blog.modules.tools.cloud.config.AliyunOssConfig;
import com.blog.modules.tools.cloud.config.MinioConfig;
import com.blog.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启审计功能 -> @EnableJpaAuditing
 *
 * @author ty
 */
@EnableAsync
@RestController
@SpringBootApplication
@MapperScan(basePackages = {
        "com.blog.modules.logging.mapper",
        "com.blog.modules.mnt.mapper",
        "com.blog.modules.quartz.mapper",
        "com.blog.modules.system.mapper",
        "com.blog.modules.tools.mapper",
        "com.blog.modules.blog.mapper",
        "com.blog.service.mapper",
})
@EnableTransactionManagement
@EnableConfigurationProperties(value = {AliyunOssConfig.class, MinioConfig.class})
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
