package com.backend.core.entities.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDTO {
    @JsonProperty("id")
    int id;

    @JsonProperty("productId")
    int productId;

    @JsonProperty("commentContent")
    String commentContent;

    @JsonProperty("replyOn")
    int replyOn;

    @JsonProperty("productColor")
    String productColor;


    public CommentRequestDTO() {
    }

    public CommentRequestDTO(int id, int productId, String commentContent, int replyOn, String productColor) {
        this.id = id;
        this.productId = productId;
        this.commentContent = commentContent;
        this.replyOn = replyOn;
        this.productColor = productColor;
    }
}
