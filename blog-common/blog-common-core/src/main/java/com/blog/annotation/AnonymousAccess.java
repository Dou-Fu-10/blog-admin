package com.blog.annotation;

import java.lang.annotation.*;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 * 用于标记匿名访问方法
 */
@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
