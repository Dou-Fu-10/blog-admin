package com.blog.modules.logging.service.dto;

import com.blog.annotation.Query;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author ty
*/
@Data
public class LogQueryParam{

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    /** 精确 */
    @Query
    private String logType;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
