package com.example.Reviewed.Dto;


import com.example.Reviewed.model.enums.Roles;
import lombok.Data;

import java.util.Set;


@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Roles> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
