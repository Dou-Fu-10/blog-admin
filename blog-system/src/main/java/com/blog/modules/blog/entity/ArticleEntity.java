package com.blog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.base.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Article)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_article")
public class ArticleEntity extends CommonEntity<ArticleEntity> {

    /**
     * 主键id
     */
    @TableId
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
     * 1表示已删除，0表示未删除
     */
    private Integer deleteFlag;


}

