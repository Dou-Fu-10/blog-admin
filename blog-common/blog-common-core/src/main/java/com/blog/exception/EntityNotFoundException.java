package com.blog.exception;

import org.springframework.util.StringUtils;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, String field, String val) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " " + val + " does not exist";
    }
}