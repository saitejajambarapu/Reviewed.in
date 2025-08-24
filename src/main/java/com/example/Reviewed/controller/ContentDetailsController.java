package com.example.Reviewed.controller;

import com.example.Reviewed.Dto.ContentDto;
//import com.example.Reviewed.model.ContentDetails;
//import com.example.Reviewed.service.ContentDetailsService;
import com.example.Reviewed.Dto.ContentDtoWithUserInteractions;
import com.example.Reviewed.serviceimpl.ApiService;
import com.example.Reviewed.serviceimpl.ContentEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/content")
@CrossOrigin(origins = "*")
public class ContentDetailsController {

//    @Autowired
//    ContentDetailsServiceImpl contentDetailsService;

    @Autowired
    ApiService apiService;

    @Autowired
    ContentEntityImpl contentEntityImpl;

//    @GetMapping()
//    public List<ContentDetails> getcontentDetails(){
//        List<ContentDetails> contentDetails= contentDetailsService.getContentDetails();
//        return contentDetails;
//    }
    @GetMapping()
    public List<ContentDtoWithUserInteractions> getContentsByTitle(@RequestParam String title) {
        return contentEntityImpl.fetchcontentByName(title);
        //return apiService.fetchMovieByTitle(title);
    }

    @GetMapping("/imdb")
    public ContentDtoWithUserInteractions getContentDetailsByImdbId(@RequestParam String imdbId){
        return  contentEntityImpl.fetchContentByImdbId(imdbId);
    }
}
