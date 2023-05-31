package com.blog.modules.system.domain.dto;

import com.blog.annotation.Query;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class UserQueryParam{

    /** 精确 */
    @Query
    private Long userId;

    private Long deptId;

    @Query(propName = "dept_id", type = Query.Type.IN)
    private Set<Long> deptIds = new HashSet<>();

    @Query(blurry = "email,username,nickName")
    private String blurry;

    /** 精确 */
    @Query
    private Boolean enabled;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
