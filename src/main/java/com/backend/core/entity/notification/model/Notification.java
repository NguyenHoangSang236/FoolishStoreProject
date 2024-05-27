package com.backend.core.entity.notification.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
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
public class Notification implements Serializable {
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "notification_date")
    Date notificationDate;
}
