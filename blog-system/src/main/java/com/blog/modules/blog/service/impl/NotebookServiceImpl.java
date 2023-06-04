package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.modules.blog.entity.NotebookEntity;
import com.blog.modules.blog.mapper.NotebookMapper;
import com.blog.modules.blog.service.NotebookService;
import org.springframework.stereotype.Service;

/**
 * (Notebook)表服务实现类
 *
 * @author IKUN
 * @since 2023-05-27 19:58:24
 */
@Service("notebookService")
public class NotebookServiceImpl extends ServiceImpl<NotebookMapper, NotebookEntity> implements NotebookService {

}

