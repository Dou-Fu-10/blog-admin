package com.blog.modules.blog.entity;

import java.util.Date;

import java.io.Serializable;

import com.alipay.api.domain.DataEntry;
import com.blog.base.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (Tag)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_tag")
public class TagEntity extends CommonEntity<ArticleEntity> {

    public TagEntity(String tagName) {
        this.tagName = tagName;
    }

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
     * 1表示已删除，0表示未删除
     */
    private Integer deleteFlag;


}

