package com.blog.modules.tools.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发送邮件时，接收参数的类
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVo {

    /**
     * 收件人，支持多个收件人
     */
    @NotEmpty
    private List<String> tos;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}
