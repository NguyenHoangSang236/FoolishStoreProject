package com.backend.core.entity.notification.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "title")
    String title;

    @Column(name = "content")
    String content;

    @Column(name = "topic")
    String topic;

    @Column(name = "additional_data")
    String additionalData;

    @Column(name = "seen")
    boolean seen;

    @Column(name = "notification_date")
    Date notificationDate;
}
