package com.example.Reviewed.service;

import com.example.Reviewed.Dto.ReviewPageDto;
import com.example.Reviewed.Dto.UserDto;

import java.util.List;


public interface ReviewPageService {
    List<ReviewPageDto> AllReviews();

    ReviewPageDto getReviewByID(long id);

    List<UserDto> setReviewLike(long reviewId);
    List<UserDto> setReviewDisLike(long reviewId);

    ReviewPageDto setReviewReply(long reviewId, String comment);

    List<UserDto> removeReviewLike(long reviewId);

    List<UserDto> removeReviewDisLike(long reviewId);
}
