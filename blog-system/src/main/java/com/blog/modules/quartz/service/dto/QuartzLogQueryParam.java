package com.blog.modules.quartz.service.dto;

import lombok.Data;
import com.blog.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author ty
* 
*/
@Data
public class QuartzLogQueryParam {

    @Query
    private Boolean isSuccess;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
