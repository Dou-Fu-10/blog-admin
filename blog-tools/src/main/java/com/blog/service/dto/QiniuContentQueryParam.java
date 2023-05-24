package com.blog.service.dto;

import lombok.Data;

import java.util.List;
import java.util.Date;
import com.blog.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author ty
* 
*/
@Data
public class QiniuContentQueryParam{

    @Query(type = Query.Type.INNER_LIKE)
    private String key;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
