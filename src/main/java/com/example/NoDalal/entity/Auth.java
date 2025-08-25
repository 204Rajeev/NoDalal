package com.example.NoDalal.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth")
public class Auth {

    @Id
    @Column(name = "session_id", length = 256, nullable = false)
    private String sessionId;

    public Integer getUserId() {
        return userId;
    }

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "login_at", nullable = false)
    private Timestamp loginAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    // Default constructor (required by JPA)
    public Auth() {
    }

    // Constructor with all fields
    public Auth(String sessionId, Integer userId, Timestamp loginAt, Timestamp expiresAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.loginAt = loginAt;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public Timestamp getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Timestamp loginAt) {
        this.loginAt = loginAt;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }
}
