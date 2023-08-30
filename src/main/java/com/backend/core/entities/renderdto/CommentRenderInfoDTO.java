package com.backend.core.entities.renderdto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_info_for_ui")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRenderInfoDTO {
    @Id
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "product_id")
    int productId;

    @Column(name = "product_color")
    String productColor;

    @Column(name = "customer_id")
    int customerId;

    @Column(name = "name")
    String name;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "comment_content")
    String commentContent;

    @Column(name = "like_quantity")
    int likeQuantity;

    @Column(name = "reply_on")
    int replyOn;
}
