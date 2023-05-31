package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.modules.blog.entity.ArticleTagEntity;
import com.blog.modules.blog.entity.TagEntity;
import com.blog.modules.blog.entity.dto.TagDto;
import com.blog.modules.blog.entity.vo.TagVo;
import com.blog.modules.blog.mapper.TagMapper;
import com.blog.modules.blog.service.ArticleTagService;
import com.blog.modules.blog.service.TagService;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (Tag)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

    @Resource
    private ArticleTagService articleTagService;

    @Override
    public List<TagEntity> getTagByTagNameList(Set<String> tagNameList) {
        return list(Wrappers.<TagEntity>lambdaQuery().in(TagEntity::getTagName, tagNameList));
    }

    @Override
    public Boolean saveTagByNameList(Set<String> tagNameList) {
        List<TagEntity> collect = tagNameList.stream().map(TagEntity::new).toList();
        return saveBatch(collect);
    }

    @Override
    public Page<TagVo> page(Page<TagEntity> page, TagDto tagDto) {
        TagEntity tagEntity = ConvertUtil.convert(tagDto, TagEntity.class);
        // 获取 标签
        Page<TagEntity> tagEntityPage = page(page, new QueryWrapper<>(tagEntity));
        // 对分页进行转换
        IPage<TagVo> convert = tagEntityPage.convert(tag -> ConvertUtil.convert(tag, TagVo.class));
        // 获取 vo 列表
        List<TagVo> tagVoList = convert.getRecords();
        // 获取标签id
        Set<Long> tagIdList = tagVoList.stream().map(TagVo::getId).collect(Collectors.toSet());
        // 通过 标签id 获取和标签绑定的文章
        List<ArticleTagEntity> articleTagEntityList = articleTagService.getArticleTagByTagIdList(tagIdList);
        // 统计 标签 标签绑定了多少文章
        Map<Long, Integer> longListHashMap = new HashMap<>();
        articleTagEntityList.forEach(articleTagEntity -> {
            // 获取文章id
            Long tid = articleTagEntity.getTid();
            int articleNum = 1;
            // map 中是否存在以 文章id 的key
            if (longListHashMap.containsKey(tid)) {
                // 存在即加以
                articleNum = longListHashMap.get(tid) + 1;
            }
            // 不存在 即将他添加到 map中
            longListHashMap.put(tid, articleNum);
        });

        tagVoList.forEach(tagVo -> {
            Integer integer = longListHashMap.get(tagVo.getId());
            if (Objects.nonNull(integer)) {
                tagVo.setArticleNum(integer);
            }
        });

        return (Page)convert.setRecords(tagVoList);
    }
}

