package com.backend.core.infrastructure.config.rsocket;

import com.backend.core.entity.comment.model.Comment;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentDataRestController {
    @Autowired
    RSocketRequester rSocketRequester;

    @GetMapping(value = "/unauthen/comment/test_rsocket_comment_id={id}")
    public Publisher<Comment> current(@PathVariable("id") int commentId) {
        return rSocketRequester.route("currentCommentData").data(commentId).retrieveMono(Comment.class);
    }

//    @GetMapping(value = "/unauthen/comment/test_rsocket_add_comment")
//    public Publisher<Void> addComment() {
//        return rSocketRequester
//                .route("addCommentData")
//                .data(getMarketData())
//                .send();
//    }
}
