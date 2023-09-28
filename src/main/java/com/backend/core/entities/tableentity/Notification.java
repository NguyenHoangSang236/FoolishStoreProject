package com.backend.core.entities.tableentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "type")
    String type;

    @Column(name = "content")
    String content;

    @Column(name = "additional_data")
    String additionalData;

    @Column(name = "notification_date")
    Date notificationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_login_account_id")
    private Account account;
}
