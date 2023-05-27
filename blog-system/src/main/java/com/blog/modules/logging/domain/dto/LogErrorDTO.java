package com.blog.modules.logging.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @author ty
*/
@Data
public class LogErrorDTO implements Serializable {

    private Long id;

    private String username;

    private String description;

    private String method;

    private String params;

    private String browser;

    private String requestIp;

    private String address;

    private Date createTime;
}