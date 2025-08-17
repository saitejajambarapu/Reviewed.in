package com.example.Reviewed.Dto;

import lombok.Data;

@Data
public class LoginDto {
    String email;
    String password;

    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
