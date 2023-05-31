package com.blog.modules.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * (Article)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 22:06:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;
    /**
     * 发布时间
     */
    private Date date;
    /**
     * 是否置顶(1true/0fales)
     */
    private Boolean top;
    /**
     * 文章字数
     */
    private Long wordCount;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章摘要
     */
    private String excerpt;
    /**
     * 文章别名
     */
    private String alias;
    /**
     * 评论数量
     */
    private Integer commentNumber;
    /**
     * 阅读量
     */
    private Integer views;
    /**
     * 草稿(1true/0fales)
     */
    private Boolean hide;
    /**
     * 点赞量
     */
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
    private String createBy;


    private Map<Long, String> categoriesList = new HashMap<>();
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

