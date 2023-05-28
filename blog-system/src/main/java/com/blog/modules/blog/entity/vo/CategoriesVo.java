package com.blog.modules.blog.entity.vo;

import java.util.Date;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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

