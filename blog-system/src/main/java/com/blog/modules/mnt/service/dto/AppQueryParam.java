package com.blog.modules.mnt.service.dto;

import lombok.Data;

import java.util.List;
import java.util.Date;
import com.blog.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author jinjin
* @date 2020-09-27
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
