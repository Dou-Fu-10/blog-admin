package com.blog.modules.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.blog.entity.TagEntity;
import com.blog.modules.blog.entity.dto.TagDto;
import com.blog.modules.blog.entity.vo.TagVo;

import java.util.List;
import java.util.Set;


/**
 * (Tag)表服务接口
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
public interface TagService extends IService<TagEntity> {

    /**
     * 通过标签名字列表获取标签
     *
     * @param tagNameList 标签名列表
     * @return 标签列表
     */
    List<TagEntity> getTagByTagNameList(Set<String> tagNameList);

    /**
     * 通过标签名字列表 保存标签
     *
     * @param tagNameList 标签名列表
     * @return 是否成功
     */
    Boolean saveTagByNameList(Set<String> tagNameList);

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param tagDto 查询实体
     * @return 所有数据
     */
    Page<TagVo> page(Page<TagEntity> page, TagDto tagDto);
}
