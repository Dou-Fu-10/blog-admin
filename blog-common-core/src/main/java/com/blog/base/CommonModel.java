package com.blog.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 抽象实体类：无公共字段
 *
 * @author ty
 **/
public abstract class CommonModel<T extends Model<?>> extends Model<T> {

}
