package com.blog.modules.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.PageInfo;
import com.blog.modules.quartz.domain.QuartzJob;
import com.blog.modules.quartz.domain.QuartzLog;
import com.blog.modules.quartz.domain.dto.QuartzJobQueryParam;
import com.blog.modules.quartz.domain.dto.QuartzLogQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface QuartzJobService extends IService<QuartzJob> {

    String CACHE_KEY = "quartzJob";

    /**
     * 查询数据分页
     *
     * @param query    条件
     * @param pageable 分页参数
     * @return PageInfo<QuartzJobDto>
     */
    PageInfo<QuartzJob> queryAll(QuartzJobQueryParam query, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param query 条件参数
     * @return List<QuartzJobDto>
     */
    List<QuartzJob> queryAll(QuartzJobQueryParam query);

    /**
     * 分页查询日志
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    PageInfo<QuartzLog> queryAllLog(QuartzLogQueryParam criteria, Pageable pageable);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @return /
     */
    List<QuartzLog> queryAllLog(QuartzLogQueryParam criteria);

    QuartzJob findById(Long id);

    /**
     * 插入一条新数据。
     */
    @Override
    boolean save(QuartzJob resources);

    @Override
    boolean updateById(QuartzJob resources);

    boolean removeById(Long id);

    boolean removeByIds(Set<Long> ids);

    /**
     * 更改定时任务状态
     *
     * @param quartzJob /
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     *
     * @param quartzJob /
     */
    void execution(QuartzJob quartzJob);

    /**
     * 执行子任务
     *
     * @param tasks /
     * @throws InterruptedException /
     */
    void executionSubJob(String[] tasks) throws InterruptedException;

    /**
     * 导出定时任务
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<QuartzJob> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 导出定时任务日志
     *
     * @param queryAllLog 待导出的数据
     * @param response    /
     * @throws IOException /
     */
    void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response) throws IOException;
}
