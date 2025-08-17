package com.example.Reviewed.Dto;

import com.example.Reviewed.model.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class UserProfileDto {
    private Long id;
    private UserEntity user;

    private List<ReviewPageDto> reviewpage;

    private List<ReviewPageDto> likedList;

    private List<ReviewPageDto> watchedList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ReviewPageDto> getReviewpage() {
        return reviewpage;
    }

    public void setReviewpage(List<ReviewPageDto> reviewpage) {
        this.reviewpage = reviewpage;
    }

    public List<ReviewPageDto> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<ReviewPageDto> likedList) {
        this.likedList = likedList;
    }

    public List<ReviewPageDto> getWatchedList() {
        return watchedList;
    }

    public void setWatchedList(List<ReviewPageDto> watchedList) {
        this.watchedList = watchedList;
    }
}
