package com.blog.modules.mnt.websocket;

import lombok.Data;

/**
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public class SocketMsg {
    private String msg;
    private MsgType msgType;

    public SocketMsg(String msg, MsgType msgType) {
        this.msg = msg;
        this.msgType = msgType;
    }
}
