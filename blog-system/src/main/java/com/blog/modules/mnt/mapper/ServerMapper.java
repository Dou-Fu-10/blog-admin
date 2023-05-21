package com.blog.modules.mnt.mapper;

import com.blog.modules.mnt.domain.Server;
import com.blog.base.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Mapper
public interface ServerMapper extends CommonMapper<Server> {
    List<Server> selectAllByDeployId(Long id);
}
