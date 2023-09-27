package com.backend.core.websocket;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    @Getter
    List<WebSocketSession> list = new ArrayList<>();

    Logger logger;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        String request = message.getPayload();
        logger.info("Server received: " + request);

        String response = String.format("response from server to '%s'", request);
        logger.info("Server sends: " + response);
        session.sendMessage(new TextMessage(response));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connected");
        super.afterConnectionEstablished(session);
    }
}
