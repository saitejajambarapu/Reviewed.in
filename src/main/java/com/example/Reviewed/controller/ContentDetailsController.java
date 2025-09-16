package com.example.Reviewed.controller;

import com.example.Reviewed.Dto.ContentDto;
//import com.example.Reviewed.model.ContentDetails;
//import com.example.Reviewed.service.ContentDetailsService;
import com.example.Reviewed.Dto.ContentDtoWithUserInteractions;
import com.example.Reviewed.Dto.ContentRequestDto;
import com.example.Reviewed.Dto.PaginatedDto;
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
    @PostMapping()
    public PaginatedDto getContentsByTitle(@RequestBody ContentRequestDto requestDto) {
        return contentEntityImpl.fetchcontentByName(requestDto);
        //return apiService.fetchMovieByTitle(title);
    }

    @PostMapping("/skippedlist/{startingValue}")
    public Boolean getContentsDetilsAfterSkipping(@PathVariable long startingValue,@RequestBody ContentRequestDto requestDto) {
        if(requestDto.getIsApi()){
            return contentEntityImpl.fetchSkippedList(startingValue,requestDto);
        }
         return true;
        //return apiService.fetchMovieByTitle(title);
    }

    @GetMapping("/imdb")
    public ContentDtoWithUserInteractions getContentDetailsByImdbId(@RequestParam String imdbId){
        return  contentEntityImpl.fetchContentByImdbId(imdbId);
    }

    @GetMapping("/powersearch")
    public ContentDto getExactContentByNameAndYear(@RequestParam String name,@RequestParam String
                                                      year){
        return apiService.getContentByNameAndYear(name,year);

    }

//    @PostMapping("/search/{contentName}")
//    public PaginatedDto getContentsByTitle(@RequestParam String C)
}
