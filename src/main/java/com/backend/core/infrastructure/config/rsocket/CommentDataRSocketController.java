package com.backend.core.infrastructure.config.rsocket;

import com.backend.core.entity.comment.model.Comment;
import com.backend.core.infrastructure.business.comment.dto.CommentRenderInfoDTO;
import com.backend.core.infrastructure.business.comment.repository.CommentRenderInfoRepository;
import com.backend.core.infrastructure.business.comment.repository.CommentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class CommentDataRSocketController {
    @Autowired
    CommentRenderInfoRepository commentRenderInfoRepo;
    @Autowired
    CommentRepository commentRepository;

    @MessageMapping("currentCommentData")
    public CommentRenderInfoDTO currentCommentData(String commentId) throws JsonProcessingException {
        CommentRenderInfoDTO commentRender = commentRenderInfoRepo.getCommentById(Integer.parseInt(commentId));

//        ObjectMapper objectMapper = new ObjectMapper();
//        String commentJson = objectMapper.writeValueAsString(commentRender);

        return commentRender;
    }

    @MessageMapping("addCommentData")
    public Mono<Void> collectMarketData(Comment comment) {
        commentRepository.save(comment);
        return Mono.empty();
    }
}
