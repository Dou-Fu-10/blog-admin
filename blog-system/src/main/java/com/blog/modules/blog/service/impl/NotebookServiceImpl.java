package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.blog.modules.blog.entity.NotebookEntity;
import com.blog.modules.blog.mapper.NotebookMapper;
import com.blog.modules.blog.service.NotebookService;

/**
 * (Notebook)表服务实现类
 *
 * @author makejava
 * @since 2023-05-27 19:43:43
 */
@Service("notebookService")
public class NotebookServiceImpl extends ServiceImpl<NotebookMapper, NotebookEntity> implements NotebookService {

}

