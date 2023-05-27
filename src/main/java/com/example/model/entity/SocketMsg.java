package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: morou
 * @Description:这里我们就不能使用简单的文本消息进行消息的发送了，我们使用json进行消息的发送。
 * 所以需要先创建一个消息对象，里面包含了消息发送者，消息接受者，消息类型（单聊还是群聊），还是就是消息，如下：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMsg implements Serializable {
    /**
     * 发送消息类型
     */
    private int type;   //聊天类型0：群聊，1：单聊.

    /**
     * 发送者的名字
     */
    private String fromUser;//发送者.

    /**
     * 接消息的人的名字
     */
    private String toUser;//接受者.

    /**
     * 消息内容是什么
     */
    private String msg;//消息O

    private static final long serialVersionUID = 1L;
}
