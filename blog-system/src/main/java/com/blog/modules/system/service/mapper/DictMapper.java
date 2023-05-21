package com.blog.modules.system.service.mapper;

import com.blog.base.CommonMapper;
import com.blog.modules.system.domain.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-24
*/
@Mapper
public interface DictMapper extends CommonMapper<Dict> {

}
