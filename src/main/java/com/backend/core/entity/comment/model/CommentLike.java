package com.backend.core.entity.comment.model;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.comment.key.CommentLikePrimaryKeys;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "comment_like")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class CommentLike {
    @EmbeddedId
    CommentLikePrimaryKeys id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @MapsId("commentId")
    @JoinColumn(name = "comment_id")
    Comment comment;
}
