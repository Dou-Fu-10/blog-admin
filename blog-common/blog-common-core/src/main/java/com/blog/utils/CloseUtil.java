package com.blog.utils;

import java.io.Closeable;

/**
 * @author IKUN
 * @description 用于关闭各种连接，缺啥补啥
 * @since 2023-05-31 21:25:43
 **/
public class CloseUtil {

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }
}
