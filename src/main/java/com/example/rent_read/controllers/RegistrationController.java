package com.example.rent_read.controllers;

import com.example.rent_read.entities.User;
import com.example.rent_read.jwt.LoginRequest;
import com.example.rent_read.jwt.LoginResponse;
import com.example.rent_read.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> list = userService.getAllUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        User user = userService.authenticate(request.getEmail(), request.getPassword());

        if(user != null){
            LoginResponse response = new LoginResponse(
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Request or Response!", HttpStatus.UNAUTHORIZED);
    }

}
