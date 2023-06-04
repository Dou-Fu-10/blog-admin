package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.PageInfo;
import com.blog.modules.system.domain.Dict;
import com.blog.modules.system.domain.dto.DictDto;
import com.blog.modules.system.domain.dto.DictQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface DictService extends IService<Dict> {

    /**
     * 查询数据分页
     *
     * @param query    条件
     * @param pageable 分页参数
     * @return map[totalElements, content]
     */
    PageInfo<DictDto> queryAll(DictQueryParam query, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param query 条件参数
     * @return List<DictDto>
     */
    List<DictDto> queryAll(DictQueryParam query);

    Dict getById(Long id);

    DictDto findById(Long id);

    @Override
    boolean save(Dict resources);

    @Override
    boolean updateById(Dict resources);

    boolean removeById(Long id);

    boolean removeByIds(Set<Long> ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictDto> all, HttpServletResponse response) throws IOException;
}
