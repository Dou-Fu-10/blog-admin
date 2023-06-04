package com.blog.modules.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Comment)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 22:06:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    /**
     * 评论表
     */
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

