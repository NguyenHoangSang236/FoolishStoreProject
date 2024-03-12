package com.backend.core.entity.comment.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikePrimaryKeys implements Serializable {
    @Column(name = "comment_id", nullable = false)
    int commentId;

    @Column(name = "customer_id", nullable = false)
    int customerId;
}
