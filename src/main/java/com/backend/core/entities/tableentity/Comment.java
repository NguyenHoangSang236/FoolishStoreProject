package com.backend.core.entities.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
