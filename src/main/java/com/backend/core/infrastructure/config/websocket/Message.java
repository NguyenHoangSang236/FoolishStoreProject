package com.backend.core.infrastructure.config.websocket;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private MessageType type;
    private String content;
    private String sender;
    private String productId;
    private String productColor;

    public enum MessageType {
        TYPING_COMMENT,
        JOIN,
        LEAVE,
        POST_COMMENT,
    }

}
