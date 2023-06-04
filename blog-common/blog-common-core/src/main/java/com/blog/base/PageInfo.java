package com.blog.base;

//import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
@Builder
@Accessors(chain = true)
public class PageInfo<T> implements Serializable {
    // @ApidelProperty("总数量")
    private long totalElements;

    // @ApidelProperty("内容")
    private List<T> content;

    public PageInfo(long totalElements, List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public PageInfo() {
    }
}
