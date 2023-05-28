package com.blog.modules.blog.entity.dto;

import java.util.Date;

import java.io.Serializable;
import java.util.Set;

import com.alipay.api.domain.DataEntry;
import com.blog.base.CommonDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (Article)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 22:06:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto  {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章标题
     */
    @NotEmpty
    private String title;
    /**
     * 发布时间
     */
    private Date date;
    /**
     * 是否置顶(1true/0fales)
     */
    private Integer top;
    /**
     * 文章字数
     */
    @NotNull
    private Long wordCount;
    /**
     * 文章内容
     */
    @NotEmpty
    private String content;
    /**
     * 文章摘要
     */
    @NotEmpty
    private String excerpt;
    /**
     * 文章别名
     */
    private String alias;
    /**
     * 评论数量
     */
    @JsonInclude
    private Integer commentNumber;
    /**
     * 阅读量
     */
    @JsonInclude
    private Integer views;
    /**
     * 草稿(1true/0fales)
     */
    private Integer hide;
    /**
     * 点赞量
     */
    @JsonInclude
    private Integer like;
    /**
     * 文章是否审核(1true/0fales)
     */
    private Integer checked;
    /**
     * 允许评论(1true/0fales)
     */
    private Integer allowRemark;
    /**
     * 创建者
     */
    @JsonInclude
    private String createBy;
    /**
     * 更新者
     */
    @JsonInclude
    private String updateBy;
    /**
     * 创建日期
     */
    @JsonInclude
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonInclude
    private Date updateTime;

    private Set<Long> categoriesList;
    /**
     * 1表示已删除，0表示未删除
     */
    @JsonInclude
    private Integer deleteFlag;


}

