package com.blog.modules.mnt.mapper;

import com.blog.modules.mnt.domain.DeployHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author ty
* 
*/
@Mapper
public interface DeployHistoryMapper extends BaseMapper<DeployHistory> {

}
