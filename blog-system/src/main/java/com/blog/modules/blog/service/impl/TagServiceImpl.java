package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.blog.modules.blog.entity.TagEntity;
import com.blog.modules.blog.mapper.TagMapper;
import com.blog.modules.blog.service.TagService;

/**
 * (Tag)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

}

