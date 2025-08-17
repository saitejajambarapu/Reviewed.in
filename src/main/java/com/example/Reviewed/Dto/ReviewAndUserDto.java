package com.example.Reviewed.Dto;

import lombok.Data;

@Data
public class ReviewAndUserDto {
    private long id;
    private String review;
    private long likes;
    private long dislikes;
    private long isReplied;
    private UserDto userDto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(long isReplied) {
        this.isReplied = isReplied;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public long isReplied() {
        return isReplied;
    }

    public void setReplied(long replied) {
        isReplied = replied;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
