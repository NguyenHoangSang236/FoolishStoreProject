package com.backend.core.entity.websocket;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketMessage {
    private MessageType type;
    private Object content;
    private String sender;

    public enum MessageType {
        TYPING_COMMENT,
        JOIN,
        LEAVE,
        POST_COMMENT,
    }

}
