package com.blog.modules.blog.entity;

import java.util.Date;

import java.io.Serializable;

import com.alipay.api.domain.DataEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (Comment)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_comment")
public class CommentEntity  {
    /**
     * 评论表
     */
    @TableId
    private Object id;

    /**
     * 文章ID
     */
    private Object gid;
    /**
     * 父级评论ID
     */
    private Object pid;
    /**
     * 置顶(1true/0fales)
     */
    private Integer top;
    /**
     * 发布人昵称
     */
    private String poster;
    /**
     * 发布人UID
     */
    private Long uid;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 是否审核(1true/0fales)
     */
    private Integer hide;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 1表示已删除，0表示未删除
     */
    private Integer deleteFlag;


}

