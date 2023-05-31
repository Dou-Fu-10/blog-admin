package com.blog.modules.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.base.CommonMapper;
import com.blog.modules.system.domain.DictDetail;
import com.blog.modules.system.domain.dto.DictDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Mapper
public interface DictDetailMapper extends CommonMapper<DictDetail> {

    List<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName);
    IPage<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName, IPage<DictDetailDto> page);
}
