package com.example.Reviewed.controller;

import com.example.Reviewed.Dto.ContentDtoWithUserInteractions;
import com.example.Reviewed.Dto.UserContentInteractionDto;
import com.example.Reviewed.serviceimpl.ContentEntityImpl;
import com.example.Reviewed.serviceimpl.UserContentInteractioServiceImpl;
import com.example.Reviewed.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserContentController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ContentEntityImpl contentEntityImpl;

    @Autowired
    UserContentInteractioServiceImpl userContentInteractioService;

    @PostMapping("/savecontentInteractions")
    public ContentDtoWithUserInteractions saveUserContentInteraction(@RequestBody ContentDtoWithUserInteractions contentInteractionDto){
        ContentDtoWithUserInteractions contentInteractionDto1 = userContentInteractioService.saveUserContentInteraction(contentInteractionDto);
        return contentInteractionDto1;
    }
}
