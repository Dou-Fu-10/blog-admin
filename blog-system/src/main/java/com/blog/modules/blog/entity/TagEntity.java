package com.blog.modules.blog.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Tag)表实体类
 *
 * @author makejava
 * @since 2023-05-27 19:43:43
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_tag")
public class TagEntity  {
    /**
     * id
     */
      @TableId
    private Long id;

    /**
     * 标签名
     */
    private String tagName;
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

