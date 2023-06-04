package com.blog.modules.quartz.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.base.PageInfo;
import com.blog.base.QueryHelpMybatisPlus;
import com.blog.commom.redis.service.RedisService;
import com.blog.modules.quartz.domain.QuartzJob;
import com.blog.modules.quartz.domain.QuartzLog;
import com.blog.modules.quartz.domain.dto.QuartzJobQueryParam;
import com.blog.modules.quartz.domain.dto.QuartzLogQueryParam;
import com.blog.modules.quartz.mapper.QuartzJobMapper;
import com.blog.modules.quartz.mapper.QuartzLogMapper;
import com.blog.modules.quartz.service.QuartzJobService;
import com.blog.modules.quartz.utils.QuartzManage;
import com.blog.utils.FileUtil;
import com.blog.utils.PageUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {

    private final QuartzManage quartzManage;
    private final QuartzJobMapper jobMapper;
    private final QuartzLogMapper logMapper;
    private final RedisService redisService;

    @Override
    public PageInfo<QuartzJob> queryAll(QuartzJobQueryParam criteria, Pageable pageable) {
        IPage<QuartzJob> page = PageUtil.toMybatisPage(pageable);
        IPage<QuartzJob> pageList = jobMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(criteria));
        PageInfo<QuartzJob> pageInfo = new PageInfo<>();
        pageInfo.setContent(pageList.getRecords());
        pageInfo.setTotalElements(pageList.getTotal());
        return pageInfo;
    }

    @Override
    public List<QuartzJob> queryAll(QuartzJobQueryParam criteria) {
        return jobMapper.selectList(QueryHelpMybatisPlus.getPredicate(criteria));
    }

    @Override
    public PageInfo<QuartzLog> queryAllLog(QuartzLogQueryParam criteria, Pageable pageable) {
        IPage<QuartzLog> page = PageUtil.toMybatisPage(pageable);
        IPage<QuartzLog> pageList = logMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(criteria));
        PageInfo<QuartzLog> pageInfo = new PageInfo<>();
        pageInfo.setContent(pageList.getRecords());
        pageInfo.setTotalElements(pageList.getTotal());
        return pageInfo;
    }

    @Override
    public List<QuartzLog> queryAllLog(QuartzLogQueryParam criteria) {
        return logMapper.selectList(QueryHelpMybatisPlus.getPredicate(criteria));
    }


    @Override
    public QuartzJob findById(Long id) {
        return jobMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(QuartzJob resources) {
        return jobMapper.insert(resources) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(QuartzJob resources) {
        boolean ret = jobMapper.updateById(resources) > 0;
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<Long> ids) {
        // delCaches(ids);
        return jobMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        this.updateById(quartzJob);
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        quartzManage.runJobNow(quartzJob);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executionSubJob(String[] tasks) throws InterruptedException {
        for (String id : tasks) {
            QuartzJob quartzJob = findById(Long.parseLong(id));
            // 执行任务
            String uuid = IdUtil.simpleUUID();
            quartzJob.setUuid(uuid);
            // 执行任务
            execution(quartzJob);
            // 获取执行状态，如果执行失败则停止后面的子任务执行
            Boolean result = (Boolean) redisService.get(uuid);
            while (result == null) {
                // 休眠5秒，再次获取子任务执行情况
                Thread.sleep(5000);
                result = (Boolean) redisService.get(uuid);
            }
            if (!result) {
                redisService.del(uuid);
                break;
            }
        }
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/

    @Override
    public void download(List<QuartzJob> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJob quartzJob : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("Spring Bean名称", quartzJob.getBeanName());
            map.put("cron 表达式", quartzJob.getCronExpression());
            map.put("状态：1暂停、0启用", quartzJob.getIsPause());
            map.put("任务名称", quartzJob.getJobName());
            map.put("方法名称", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("备注", quartzJob.getDescription());
            map.put("负责人", quartzJob.getPersonInCharge());
            map.put("报警邮箱", quartzJob.getEmail());
            map.put("子任务ID", quartzJob.getSubTask());
            map.put("任务失败后是否暂停", quartzJob.getPauseAfterFailure());
            map.put("创建者", quartzJob.getCreateBy());
            map.put("更新者", quartzJob.getUpdateBy());
            map.put("创建日期", quartzJob.getCreateTime());
            map.put("更新时间", quartzJob.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzLog quartzLog : queryAllLog) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzLog.getJobName());
            map.put("Bean名称", quartzLog.getBeanName());
            map.put("执行方法", quartzLog.getMethodName());
            map.put("参数", quartzLog.getParams());
            map.put("表达式", quartzLog.getCronExpression());
            map.put("异常详情", quartzLog.getExceptionDetail());
            map.put("耗时/毫秒", quartzLog.getTime());
            map.put("状态", quartzLog.getIsSuccess() ? "成功" : "失败");
            map.put("创建日期", quartzLog.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
