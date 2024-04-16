package com.backend.core.infrastructure.config.rsocket;

import com.backend.core.entity.comment.model.Comment;
import com.backend.core.infrastructure.business.comment.dto.CommentRenderInfoDTO;
import com.backend.core.infrastructure.business.comment.repository.CommentRenderInfoRepository;
import com.backend.core.infrastructure.business.comment.repository.CommentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class CommentDataRSocketController {
    @Autowired
    CommentRenderInfoRepository commentRenderInfoRepo;
    @Autowired
    CommentRepository commentRepo;

    @MessageMapping("currentCommentData")
    public Map<String, Object> currentCommentData(String commentId) throws JsonProcessingException {
        CommentRenderInfoDTO commentRenderInfo = commentRenderInfoRepo.getCommentById(Integer.parseInt(commentId));

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(commentRenderInfo, new TypeReference<Map<String, Object>>() {});

        return map;
    }

    @MessageMapping("addCommentData")
    public Mono<Void> collectMarketData(Comment comment) {
        commentRepo.save(comment);
        return Mono.empty();
    }
}
