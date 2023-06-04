package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.PageInfo;
import com.blog.modules.system.domain.Job;
import com.blog.modules.system.domain.dto.JobDto;
import com.blog.modules.system.domain.dto.JobQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface JobService extends IService<Job> {

    PageInfo<JobDto> queryAll(JobQueryParam query, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param query 条件参数
     * @return List<JobDto>
     */
    List<JobDto> queryAll(JobQueryParam query);

    List<JobDto> queryAll();

    Job getById(Long id);

    /**
     * 插入一条新数据。
     */
    @Override
    boolean save(Job resources);

    @Override
    boolean updateById(Job resources);

    boolean removeById(Long id);

    boolean removeByIds(Set<Long> ids);

    void verification(Set<Long> ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<JobDto> all, HttpServletResponse response) throws IOException;
}
