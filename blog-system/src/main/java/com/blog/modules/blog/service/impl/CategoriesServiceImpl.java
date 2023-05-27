package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.mapper.CategoriesMapper;
import com.blog.modules.blog.service.CategoriesService;

/**
 * (Categories)表服务实现类
 *
 * @author makejava
 * @since 2023-05-27 19:43:39
 */
@Service("categoriesService")
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, CategoriesEntity> implements CategoriesService {

}

