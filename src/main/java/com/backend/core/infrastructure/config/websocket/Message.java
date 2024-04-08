package com.backend.core.infrastructure.config.websocket;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private MessageType type;
    private String content;
    private String sender;
    private String productId;
    private String productColor;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}
