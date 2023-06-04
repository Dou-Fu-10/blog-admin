package com.blog.modules.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Categories)表实体类
 *
 * @author IKUN
 * @since 2023-05-28 13:20:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDto {
    /**
     * 分类id
     */
    private Long id;

    /**
     * 分类名
     */
    @NotEmpty
    private String name;
    /**
     * 别名
     */
    private String alias;
    /**
     * 排序序号
     */
    private Integer taxis;
    /**
     * 父分类ID(0即是顶级父类)
     */
    @NotNull
    private Long pid;
    /**
     * 备注
     */
    private String description;
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

