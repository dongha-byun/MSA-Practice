package com.example.userservice.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String encryptPassword;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public User(String userId, String email, String name, String encryptPassword, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.encryptPassword = encryptPassword;
        this.createdAt = createdAt;
    }
}
