package com.blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.blog.modules.blog.entity.InfoEntity;
import com.blog.modules.blog.mapper.InfoMapper;
import com.blog.modules.blog.service.InfoService;

/**
 * (Info)表服务实现类
 *
 * @author IKUN
 * @since 2023-06-05 22:47:33
 */
@Service("infoService")
public class InfoServiceImpl extends ServiceImpl<InfoMapper, InfoEntity> implements InfoService {

}

