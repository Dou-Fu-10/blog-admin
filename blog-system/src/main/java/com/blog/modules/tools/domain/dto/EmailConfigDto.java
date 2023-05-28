package com.blog.modules.tools.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author ty
* 
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailConfigDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String fromUser;

    private String host;

    private String pass;

    private String port;

    private String user;
}
