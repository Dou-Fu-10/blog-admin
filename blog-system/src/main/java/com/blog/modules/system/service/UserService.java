package com.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.PageInfo;
import com.blog.modules.system.domain.User;
import com.blog.modules.system.domain.dto.UserDto;
import com.blog.modules.system.domain.dto.UserQueryParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public interface UserService extends IService<User> {

    /**
     * 查询数据分页
     *
     * @param query    条件
     * @param pageable 分页参数
     * @return PageInfo<UserDto>
     */
    PageInfo<UserDto> queryAll(UserQueryParam query, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param query 条件参数
     * @return List<UserDto>
     */
    List<UserDto> queryAll(UserQueryParam query);

    User getById(Long id);

    UserDto findById(Long id);

    /**
     * 根据用户名查询
     *
     * @param userName /
     * @return /
     */
    User getByUsername(String userName);

    UserDto findByName(String userName);

    /**
     * 插入一条新数据。
     */
    boolean save(UserDto resources);

    boolean updateById(UserDto resources) throws Exception;

    boolean removeById(Long id);

    boolean removeByIds(Set<Long> ids);

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param file 文件
     * @return /
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 用户自助修改资料
     *
     * @param resources /
     */
    void updateCenter(User resources);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<UserDto> all, HttpServletResponse response) throws IOException;

}
