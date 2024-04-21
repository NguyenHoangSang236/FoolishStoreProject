package com.backend.core.entity.websocket;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketMessage implements Serializable {
    private MessageType type;
    private Object content;
    private String sender;

    public enum MessageType {
        TYPING_COMMENT,
        JOIN,
        LEAVE,
        POST_COMMENT,
        LIKE_COMMENT,
        UNLIKE_COMMENT,
    }

}
