package com.blog.modules.mnt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.PageInfo;
import com.blog.modules.mnt.domain.DeployHistory;
import com.blog.modules.mnt.service.dto.DeployHistoryDto;
import com.blog.modules.mnt.service.dto.DeployHistoryQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author ty
 */
public interface DeployHistoryService extends IService<DeployHistory> {

    String CACHE_KEY = "deployHistory";

    /**
     * 查询数据分页
     * @param query 条件
     * @param pageable 分页参数
     * @return PageInfo<DeployHistoryDto>
     */
    PageInfo<DeployHistoryDto> queryAll(DeployHistoryQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<DeployHistoryDto>
    */
    List<DeployHistoryDto> queryAll(DeployHistoryQueryParam query);

    DeployHistory getById(Long id);
    DeployHistoryDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    @Override
    boolean save(DeployHistory resources);
    @Override
    boolean updateById(DeployHistory resources);
    boolean removeById(String id);
    boolean removeByIds(Set<String> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<DeployHistoryDto> all, HttpServletResponse response) throws IOException;
}
