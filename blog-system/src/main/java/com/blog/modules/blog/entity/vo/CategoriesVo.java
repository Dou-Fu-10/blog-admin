package com.blog.modules.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * (Categories)表实体类
 *
 * @author IKUN
 * @since 2023-05-28 13:20:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesVo {
    /**
     * 分类id
     */
    @JsonIgnore
    private Long id;

    /**
     * 分类名
     */
    private String name;
    /**
     * 别名
     */
    @JsonIgnore
    private String alias;
    /**
     * 排序序号
     */
    @JsonIgnore
    private Integer taxis;
    /**
     * 父分类ID(0即是顶级父类)
     */
    private Long pid;
    /**
     * 父分类ID(0即是顶级父类)
     */
    private List<CategoriesVo> children;
    /**
     * 父分类ID(0即是顶级父类)
     */
    private Integer articlesNumber;
    /**
     * 备注
     */
    @JsonIgnore
    private String description;
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
     * 1表示已删除，0表示未删除
     */
    @JsonIgnore
    private Integer deleteFlag;


}

