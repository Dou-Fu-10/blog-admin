package com.blog.modules.blog.entity.dto;

import com.blog.base.CommonDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

/**
 * (Article)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 22:06:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto extends CommonDto {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章标题
     */
    @NotEmpty(message = "请填写文章标题")
    private String title;
    /**
     * 发布时间
     */
    @NotNull(message = "请填写发布时间")
    private Date date;
    /**
     * 是否置顶(1true/0fales)
     */
    @NotNull(message = "是否置顶")
    private Boolean top;
    /**
     * 文章字数
     */
    @NotNull(message = "添加出错请联系管理员")
    private Long wordCount;
    /**
     * 文章内容
     */
    @NotEmpty(message = "请填写文章内容")
    private String content;
    /**
     * 文章摘要
     */
    @NotEmpty(message = "请填写文章摘要")
    private String excerpt;
    /**
     * 文章别名
     */
    private String alias;
    /**
     * 评论数量
     */
    @JsonIgnore
    private Integer commentNumber;
    /**
     * 阅读量
     */
    @JsonIgnore
    private Integer views;
    /**
     * 草稿(1true/0fales)
     */
    @NotNull(message = "文章是否是草稿")
    private Boolean hide;
    /**
     * 点赞量
     */
    @JsonIgnore
    private Integer favorite;
    /**
     * 文章是否审核(1true/0fales)
     */
    private Boolean checked;
    /**
     * 允许评论(1true/0fales)
     */
    private Boolean allowRemark;
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
     * 分类
     */
    @NotNull(message = "请填写至少一个分类")
    private Set<Long> categoriesList;
    /**
     * 标签
     */
    @NotNull(message = "请填写文章标题")
    private Set<String> tagList;

    /**
     * 1表示已删除，0表示未删除
     */
    @JsonIgnore
    private Integer deleteFlag;


}

