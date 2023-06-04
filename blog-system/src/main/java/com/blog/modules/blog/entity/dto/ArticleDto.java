package com.blog.modules.blog.entity.dto;

import com.blog.base.CommonDto;
import com.blog.base.ValidationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

/**
 * (Article)表实体类
 *
 * @author IKUN
 * @since 2023-05-27 22:06:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto extends CommonDto {
    /**
     * 主键id
     */
    @Null(message = "请正确的填写信息", groups = ValidationDto.Insert.class)
    @NotNull(message = "请正确的填写信息", groups = ValidationDto.Update.class)
    private Long id;

    /**
     * 文章标题
     */
    @NotEmpty(message = "请填写文章标题", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private String title;
    /**
     * 发布时间
     */
    @Null(message = "不能修改发布时间", groups = {ValidationDto.Update.class})
    private Date date;
    /**
     * 是否置顶(1true置顶/0fales不置顶)
     */
    @NotNull(message = "是否置顶", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private Boolean top;
    /**
     * 文章字数
     */
    @NotNull(message = "添加出错请联系管理员", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private Long wordCount;
    /**
     * 文章内容
     */
    @NotEmpty(message = "请填写文章内容", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private String content;
    /**
     * 文章摘要
     */
    @NotEmpty(message = "请填写文章摘要", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private String excerpt;
    /**
     * 文章别名
     */
    @Null(message = "别名由系统生成")
    private String alias;
    /**
     * 评论数量
     */
    @JsonIgnore
    private Integer commentNumber;
    /**
     * 阅读量
     */
    @JsonIgnore
    private Integer views;
    /**
     * 草稿(1true是草稿/0fales不是草稿)
     */
    @NotNull(message = "文章是否是草稿")
    private Boolean hide;
    /**
     * 点赞量
     */
    @JsonIgnore
    private Integer favorite;
    /**
     * 审核(1true审核完成/0fales没有审核)
     */
    private Boolean checked;
    /**
     * 允许评论(1true允许评论/0fales不允许评论)
     */
    private Boolean allowRemark;
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
     * 分类
     */
    @NotNull(message = "请填写至少一个分类", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private Set<Long> categoriesList;
    /**
     * 标签
     */
    @NotNull(message = "请填写文章标题", groups = {ValidationDto.Insert.class, ValidationDto.Update.class})
    private Set<String> tagList;

    /**
     * 1表示已删除，0表示未删除
     */
    @JsonIgnore
    private Integer deleteFlag;


}

