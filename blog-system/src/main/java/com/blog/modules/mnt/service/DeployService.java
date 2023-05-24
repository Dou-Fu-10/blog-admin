package com.blog.modules.mnt.service;

import com.blog.modules.mnt.domain.Deploy;
import com.blog.modules.mnt.domain.DeployHistory;
import com.blog.modules.mnt.service.dto.DeployDto;
import com.blog.modules.mnt.service.dto.DeployQueryParam;

import com.blog.base.PageInfo;
import org.springframework.data.domain.Pageable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import java.util.Set;

/**
* @author ty
* 
*/
public interface DeployService  extends IService<Deploy>{

    static final String CACHE_KEY = "deploy";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<DeployDto>
    */
    PageInfo<DeployDto> queryAll(DeployQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<DeployDto>
    */
    List<DeployDto> queryAll(DeployQueryParam query);

    Deploy getById(Long id);
    DeployDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    boolean save(DeployDto resources);
    boolean updateById(DeployDto resources);
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    void deploy(String fileSavePath, Long id);
    String serverStatus(DeployDto resources);
    String startServer(DeployDto resources);
    String stopServer(DeployDto resources);
    String serverReduction(DeployHistory resources);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<DeployDto> all, HttpServletResponse response) throws IOException;
}
