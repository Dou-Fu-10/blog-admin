package com.blog.modules.mnt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.PageInfo;
import com.blog.modules.mnt.domain.Database;
import com.blog.modules.mnt.domain.dto.DatabaseDto;
import com.blog.modules.mnt.domain.dto.DatabaseQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author ty
 */
public interface DatabaseService extends IService<Database> {

    String CACHE_KEY = "database";

    /**
     * 查询数据分页
     * @param query 条件
     * @param pageable 分页参数
     * @return PageInfo<DatabaseDto>
     */
    PageInfo<DatabaseDto> queryAll(DatabaseQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<DatabaseDto>
    */
    List<DatabaseDto> queryAll(DatabaseQueryParam query);

    Database getById(String id);
    DatabaseDto findById(String id);

    /**
     * 插入一条新数据。
     */
    @Override
    boolean save(Database resources);
    @Override
    boolean updateById(Database resources);
    boolean removeById(String id);
    boolean removeByIds(Set<String> ids);

    boolean testConnection(Database resources);
    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<DatabaseDto> all, HttpServletResponse response) throws IOException;
}
