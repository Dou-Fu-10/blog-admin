package com.blog.modules.mnt.websocket;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
public enum MsgType {
    /**
     * 连接
     */
    CONNECT,
    /**
     * 关闭
     */
    CLOSE,
    /**
     * 信息
     */
    INFO,
    /**
     * 错误
     */
    ERROR
}
