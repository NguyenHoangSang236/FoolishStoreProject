package com.backend.core.entity.comment.model;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.product.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comments")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @JsonIgnore
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "product_color")
    String productColor;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(name = "Comment_Content", columnDefinition = "text")
    String commentContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "Comment_Date")
    Date commentDate;

    @Column(name = "like_quantity")
    int likeQuantity;

    @Column(name = "reply_on")
    int replyOn;

    @Column(name = "reply_quantity")
    int replyQuantity;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = {CascadeType.ALL})
    List<CommentLike> commentLike;


    public void likeComment() {
        this.likeQuantity++;
    }

    public void unlikeComment() {
        this.likeQuantity--;
    }

    public void replyComment() {
        this.replyQuantity++;
    }
}
