//package com.backend.core.websocket;
//
//import jakarta.servlet.http.HttpSession;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//public class HttpHandshakeInterceptor implements HandshakeInterceptor {
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
////        if (request instanceof ServerHttpRequest) {
////            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
////            HttpSession session = servletRequest.getServletRequest().getSession();
////            attributes.put("sessionId", session.getId());
////        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {
//        System.out.println(exception.fillInStackTrace());
//    }
//}