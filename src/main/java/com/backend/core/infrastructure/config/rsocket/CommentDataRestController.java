package com.backend.core.infrastructure.config.rsocket;

import com.backend.core.infrastructure.business.comment.dto.CommentRenderInfoDTO;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentDataRestController {
    private final RSocketRequester rSocketRequester;

    public CommentDataRestController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping(value = "/unauthen/comment/test_rsocket_comment_id={id}")
    public Publisher<CommentRenderInfoDTO> current(@PathVariable("id") int commentId) {
        return rSocketRequester
                .route("currentCommentData")
                .data(commentId)
                .retrieveMono(CommentRenderInfoDTO.class);
    }

//    @GetMapping(value = "/unauthen/comment/test_rsocket_add_comment")
//    public Publisher<Void> addComment() {
//        return rSocketRequester
//                .route("addCommentData")
//                .data(getMarketData())
//                .send();
//    }
}
