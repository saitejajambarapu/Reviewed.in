package com.example.Reviewed.controller;

import com.example.Reviewed.Dto.UserProfileDto;
import com.example.Reviewed.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/myprofile/{id}")
    public ResponseEntity<?> getMyProfile(@PathVariable Long id){
        UserProfileDto userProfileDto = userService.getProfileById(id);
        return ResponseEntity.ok(userProfileDto);
    }
}
