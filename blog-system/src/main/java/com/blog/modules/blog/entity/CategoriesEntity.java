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
 * (Categories)表实体类
 *
 * @author IKUN
 * @since 2023-05-28 13:20:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_categories")
public class CategoriesEntity extends CommonEntity<CategoriesEntity> {
    /**
     * 分类id
     */
    @TableId
    private Long id;

    /**
     * 分类名
     */
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
    private Long pid;
    /**
     * 备注
     */
    private String description;
    /**
     * 1表示已删除，0表示未删除
     */
    private Integer deleteFlag;


}

