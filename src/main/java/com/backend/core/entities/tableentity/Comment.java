package com.backend.core.entities.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "comments")
@DynamicInsert
@DynamicUpdate
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @ManyToOne
    @JsonIgnore
    @JsonManagedReference
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

    @Column(name = "Comment_Date")
    Date commentDate;

    @Column(name = "like_quantity")
    int likeQuantity;

    @Column(name = "reply_on")
    int replyOn;


    public Comment() {
    }

    public Comment(int id, Product product, String productColor, Customer customer, String commentContent, Date commentDate, int likeQuantity, int replyOn) {
        this.id = id;
        this.product = product;
        this.productColor = productColor;
        this.customer = customer;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.likeQuantity = likeQuantity;
        this.replyOn = replyOn;
    }

    public void likeComment() {
        this.likeQuantity++;
    }
}
