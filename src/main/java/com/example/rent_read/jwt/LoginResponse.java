package com.example.rent_read.jwt;

import com.example.rent_read.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String email;
    private String firstName;
    private String lastName;
    private Role role;


}
