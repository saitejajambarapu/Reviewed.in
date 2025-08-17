package com.example.Reviewed.Dto;

import com.example.Reviewed.model.ContentReviews;
import com.example.Reviewed.model.ReviewReplies;
import lombok.Data;

import java.util.List;

@Data
public class ReviewPageDto {
    private Long id;

    private String contentName;

    private String contentPoster;

    private ReviewAndUserDto contentReviews;

    private List<ReivewRepliesDto>  reviewReplies;

    private List<UserDto> isLiked;

    private List<UserDto> disLiked;
    private float rating;

    private boolean isLikedList;

    private boolean isWatchList;

    public boolean isLikedList() {
        return isLikedList;
    }

    public void setLikedList(boolean likedList) {
        isLikedList = likedList;
    }

    public boolean isWatchList() {
        return isWatchList;
    }

    public void setWatchList(boolean watchList) {
        isWatchList = watchList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentPoster() {
        return contentPoster;
    }

    public void setContentPoster(String contentPoster) {
        this.contentPoster = contentPoster;
    }

    public ReviewAndUserDto getContentReviews() {
        return contentReviews;
    }

    public void setContentReviews(ReviewAndUserDto contentReviews) {
        this.contentReviews = contentReviews;
    }

    public List<ReivewRepliesDto> getReviewReplies() {
        return reviewReplies;
    }

    public void setReviewReplies(List<ReivewRepliesDto> reviewReplies) {
        this.reviewReplies = reviewReplies;
    }

    public List<UserDto> getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(List<UserDto> isLiked) {
        this.isLiked = isLiked;
    }

    public List<UserDto> getDisLiked() {
        return disLiked;
    }

    public void setDisLiked(List<UserDto> disLiked) {
        this.disLiked = disLiked;
    }
}
