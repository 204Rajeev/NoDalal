package com.example.NoDalal.service;

import com.example.NoDalal.entity.User;
import com.example.NoDalal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with ID: " + id));
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.deleteById(id);
            return user;
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public boolean doesUserNameExist(String name) {
        return userRepository.existsByUserName(name);
    }

    public boolean doesEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public User authenticate(String email, String userName) {
        return userRepository.findByEmailAndUserName(email, userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or username"));
    }


}
