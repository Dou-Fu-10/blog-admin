package com.blog.modules.blog.entity.dto;

import com.blog.base.ValidationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Notebook)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 22:06:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotebookDto {
    /**
     * id
     */
    @Null
    @NotEmpty(groups = ValidationDto.Update.class)
    private Long id;

    /**
     * 笔记
     */
    @NotEmpty
    private String notebook;
    /**
     * 创建者
     */
    @JsonIgnore
    private String createBy;
    /**
     * 更新者
     */
    @JsonIgnore
    private String updateBy;
    /**
     * 创建日期
     */
    @JsonIgnore
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;
    /**
     * 1表示已删除，0表示未删除
     */
    @JsonIgnore
    private Integer deleteFlag;


}

