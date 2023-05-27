package com.blog.modules.mnt.domain.dto;

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
public class DatabaseQueryParam{

    /** 模糊 */
    @Query(blurry = "name,userName,jdbcUrl")
    private String blurry;

    /** 精确 */
    @Query
    private String jdbcUrl;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
