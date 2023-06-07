package com.backend.core.entity.tableentity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(name = "Comment_Content", columnDefinition = "text")
    String content;

    @Column(name = "Comment_Date")
    Date commentDate;


    public Comment() {}

    public Comment(int id, Product product, Customer customer, String content, Date commentDate) {
        super();
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.content = content;
        this.commentDate = commentDate;
    }
}
