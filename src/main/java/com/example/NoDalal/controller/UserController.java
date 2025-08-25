package com.example.NoDalal.controller;

import com.example.NoDalal.DTO.LoginRequestDTO;
import com.example.NoDalal.DTO.LoginResponseDTO;
import com.example.NoDalal.entity.Auth;
import com.example.NoDalal.entity.User;
import com.example.NoDalal.service.AuthService;
import com.example.NoDalal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private AuthService authService;

    @GetMapping
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public User getProfile(HttpServletRequest request) {
        // Spring gives you the request object here
        Integer userId = (Integer) request.getAttribute("userId");
        return userService.getUserById(userId);
    }


    @PostMapping("save")
    public String saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return  savedUser.toString();
    }

    @DeleteMapping("/delete")
    public User deleteMyself(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return userService.deleteUser(userId);
    }


    @GetMapping("/check-username")
    public boolean checkUsername(@RequestParam String name) {
        return userService.doesUserNameExist(name);
    }

    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        return userService.doesEmailExist(email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        User user = userService.authenticate(request.getEmail(), request.getUserName());
        // Optional: set session expiration (e.g., 1 year from now)
        Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000);

        Auth auth = authService.createSession(user.getUserId(), expiresAt);
        return ResponseEntity.ok(new LoginResponseDTO(
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUserId(),
                auth.getSessionId()
        ));
    }



    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResponseStatusException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", ex.getStatusCode().value());
        error.put("error", ex.getStatusCode().getClass());
        error.put("message", ex.getReason()); // this line ensures the message is included
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

}
