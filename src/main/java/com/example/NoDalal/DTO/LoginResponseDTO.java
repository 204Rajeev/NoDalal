package com.example.NoDalal.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LoginResponseDTO {

    private String userName;
    private String email;
    private Timestamp createdAt;
    private int userId;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public LoginResponseDTO(String userName, String email, Timestamp createdAt, int userId, String sessionId) {
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.userId = userId;
        this.sessionId = sessionId;
    }
}
