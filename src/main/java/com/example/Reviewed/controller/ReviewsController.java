package com.example.Reviewed.controller;


import com.example.Reviewed.Dto.ReviewPageDto;
import com.example.Reviewed.Dto.UserDto;
import com.example.Reviewed.service.ReviewPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewsController {

    private final ReviewPageService reviewPageService;

    @Autowired
    public ReviewsController(ReviewPageService reviewPageService) {
        this.reviewPageService = reviewPageService;
    }

    @GetMapping()
    public ResponseEntity<?> AllReviews(){
        List<ReviewPageDto> reviewPageDtoList = reviewPageService.AllReviews();
        return ResponseEntity.ok(reviewPageDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable long id){
    ReviewPageDto reviewPageDto = reviewPageService.getReviewByID(id);
    return  ResponseEntity.ok(reviewPageDto);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?>  setReviewLike(@PathVariable("id") long reviewId){
        List<UserDto> isliked = reviewPageService.setReviewLike(reviewId);
        return ResponseEntity.ok(isliked);
    }
    @PostMapping("/removelike/{id}")
    public ResponseEntity<?>  removeReviewLike(@PathVariable("id") long reviewId){
        List<UserDto> isliked = reviewPageService.removeReviewLike(reviewId);
        return ResponseEntity.ok(isliked);
    }
    @PostMapping("/dislike/{id}")
    public ResponseEntity<?>  setReviewDisLike(@PathVariable("id") long reviewId){
        List<UserDto> isDisliked = reviewPageService.setReviewDisLike(reviewId);
        return ResponseEntity.ok(isDisliked);
    }
    @PostMapping("/removedislike/{id}")
    public ResponseEntity<?>  removeReviewDisLike(@PathVariable("id") long reviewId){
        List<UserDto> isDisliked = reviewPageService.removeReviewDisLike(reviewId);
        return ResponseEntity.ok(isDisliked);
    }

    @PostMapping("reviewcoment/{id}")
    public ResponseEntity<?>  setReviewReply(@PathVariable("id") long reviewId,@RequestBody String comment){
        ReviewPageDto reviewPageDto = reviewPageService.setReviewReply(reviewId, comment);
        return ResponseEntity.ok(reviewPageDto);
    }
}
