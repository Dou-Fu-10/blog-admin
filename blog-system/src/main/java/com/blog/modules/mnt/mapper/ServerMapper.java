package com.blog.modules.mnt.mapper;

import com.blog.base.CommonMapper;
import com.blog.modules.mnt.domain.Server;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface ServerMapper extends CommonMapper<Server> {
    List<Server> selectAllByDeployId(Long id);
}
