package com.blog.modules.blog.entity.vo;

import java.util.Date;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Integer top;
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
    private Integer hide;
    /**
     * 点赞量
     */
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
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 1表示已删除，0表示未删除
     */
    private Integer deleteFlag;


}

