package com.blog.modules.mnt.mapper;

import com.blog.modules.mnt.domain.Server;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author ty
* 
*/
@Mapper
public interface ServerMapper extends BaseMapper<Server> {
    List<Server> selectAllByDeployId(Long id);
}
