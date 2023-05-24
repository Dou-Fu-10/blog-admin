package com.blog.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 浅析VO、DTO、DO、PO的概念、区别和用处
 * https://www.cnblogs.com/qixuejia/p/4390086.html
 *
 * @author ty
 */
@Data
public abstract class CommonDto implements Serializable{

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

}
