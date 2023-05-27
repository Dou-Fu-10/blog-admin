package com.blog.modules.tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modules.tools.domain.LocalStorage;
import com.blog.modules.tools.domain.dto.LocalStorageDto;
import com.blog.modules.tools.domain.dto.LocalStorageQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
* @author ty
*/
public interface LocalStorageService extends IService<LocalStorage> {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(LocalStorageQueryParam criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<LocalStorageDto> queryAll(LocalStorageQueryParam criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    LocalStorageDto findById(Long id);

    /**
     * 上传
     * @param name 文件名称
     * @param file 文件
     * @return
     */
    LocalStorage create(String name, MultipartFile file);

    /**
     * 编辑
     * @param resources 文件信息
     */
    void update(LocalStorage resources);

    /**
     * 多选删除
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     * @param localStorageDtos 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<LocalStorageDto> localStorageDtos, HttpServletResponse response) throws IOException;
}