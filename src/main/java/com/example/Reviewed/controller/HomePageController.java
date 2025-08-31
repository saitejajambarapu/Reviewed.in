package com.example.Reviewed.controller;

import com.example.Reviewed.Dto.HomePageDto;
import com.example.Reviewed.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Home")
@CrossOrigin(origins = "*")
public class HomePageController {

    @Autowired
    HomePageService homePageService;

    @GetMapping
    public HomePageDto getHomePageDetails(){
        HomePageDto homePageDto = homePageService.getHomePageDetails();
        return homePageDto;
    }

}
