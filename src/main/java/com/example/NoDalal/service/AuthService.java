package com.example.NoDalal.service;

import com.example.NoDalal.entity.Auth;
import com.example.NoDalal.repository.AuthRepository;
import com.example.NoDalal.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    public Auth createSession(Integer userId, Timestamp expiresAt) {
        String sessionId = UUID.randomUUID().toString();
        Timestamp loginAt = new Timestamp(System.currentTimeMillis());
        Auth auth = new Auth(sessionId, userId, loginAt, expiresAt);
        return authRepository.save(auth);
    }



}
