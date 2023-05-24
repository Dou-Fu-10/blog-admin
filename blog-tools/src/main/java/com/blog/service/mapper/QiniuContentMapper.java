package com.blog.service.mapper;

import com.blog.domain.QiniuContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author ty
* 
*/
@Mapper
public interface QiniuContentMapper extends BaseMapper<QiniuContent> {

    QiniuContent findByKey(String key);
}
