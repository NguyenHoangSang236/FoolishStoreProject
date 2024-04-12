package com.backend.core.infrastructure.config.rsocket;

import com.backend.core.entity.comment.model.Comment;
import com.backend.core.infrastructure.business.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class CommentDataRSocketController {
    @Autowired
    CommentRepository commentRepository;

    @MessageMapping("currentCommentData")
    public Comment currentCommentData(int commentId) {
        return commentRepository.getCommentById(commentId);
    }

    @MessageMapping("addCommentData")
    public Mono<Void> collectMarketData(Comment comment) {
        commentRepository.save(comment);
        return Mono.empty();
    }
}
