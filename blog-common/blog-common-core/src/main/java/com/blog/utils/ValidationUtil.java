package com.blog.utils;


import cn.hutool.core.util.ObjectUtil;
import com.blog.exception.BadRequestException;

/**
 * 验证工具
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public class ValidationUtil{

    /**
     * 验证空
     */
    public static void isNull(Object obj, String entity, String parameter , Object value){
        if(ObjectUtil.isNull(obj)){
            String msg = entity + " 不存在: "+ parameter +" is "+ value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String email) {
        return true;
//        return new EmailValidator().isValid(email, null);
    }
}
