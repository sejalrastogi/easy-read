package com.example.rent_read.services;

import com.example.rent_read.entities.Role;
import com.example.rent_read.entities.User;
import com.example.rent_read.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return new ResponseEntity<>("User already present!", HttpStatus.CONFLICT);
        }

        user.setRole(Role.USER);
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public List<User> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list;
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }
}
