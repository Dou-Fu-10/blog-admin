package com.blog.modules.mnt.domain.dto;

import com.blog.annotation.Query;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class AppQueryParam{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
